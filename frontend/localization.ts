import { Binder, Size } from '@vaadin/form';
import {get} from "lit-translate";

// See https://beanvalidation.org/2.0-jsr380/spec/#validationapi-message for inspiration

// Utils for typing MessageInterpolatorWithNullableResult
type ArgumentTypes<T> = T extends (... args: infer U ) => infer R ? U: never;
type ReplaceReturnType<T, TNewReturn> = (...a: ArgumentTypes<T>) => TNewReturn;

type MessageProcessor = NonNullable<typeof Binder.interpolateMessageCallback>;
type MessageProcessorWithNullableResult = ReplaceReturnType<MessageProcessor, string | undefined>;

function escapeRegex(string: string) {
    return string.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
}

let messages: Map<string | RegExp, string> = new Map();

const getMessageFromMap: MessageProcessorWithNullableResult = (searchStr: string) => {
    if (messages.has(searchStr)) {
        return messages.get(searchStr)!;
    }
    return undefined;
}

// Adds regex support to messages
const getMessageByRegexMatch: MessageProcessorWithNullableResult = (searchStr: string) => {
    for (const key of messages.keys()) {
        if (key instanceof RegExp) {
            const found = searchStr.match(key);
            if (found) {
                let message = messages.get(key) as string;
                // Interpolate regex capturing groups into message
                if (found.length > 1) { // has capturing group matches
                    // Interpolate indexed placeholders
                    for (let i = 1; i <= found.length; i++) {
                        const re = new RegExp(escapeRegex(`{${i}}`), 'g');
                        message = message.replace(re, found[i]);
                    }
                    // Interpolate named placeholders
                    if (found.groups) {
                        for (const [key, value] of Object.entries(found.groups)) {
                            const re = new RegExp(escapeRegex(`{${key}}`), 'g');
                            message = message.replace(re, value);
                        }
                    }
                }

                return message;
            }
        }
    }
    return undefined;
}

// Interpolation: {size} = binderNode.value.length
const interpolateSize: MessageProcessor = (message, validator, binderNode) => {
    if (validator instanceof Size) {
        message = message.replace(/(?<!\\){size}/g, String(binderNode.value.length));
    }
    return message;
};

// Interpolation: {fieldValue} = binderNode.value
const interpolateFieldValue: MessageProcessor = (message, validator, binderNode) => {
    if (validator instanceof Size) {
        message = message.replace(/(?<!\\){size}/g, String(binderNode.value.length));
    }
    return message;
};

// Interpolate remaining placeholders from validator properties (results in "undefined" if property not defined)
const interpolateValidatorProperties: MessageProcessor = (message, validator, _binderNode) => {
    const placeholders = new Set(Array.from(message.matchAll(/(?<!\\){(.+?)}/g)).map(a => (a as any)[1]));
    placeholders.forEach((placeholder) => {
        // Get the replacement value from a property of the validator object (uses name of the placeholder to look for a property)
        const replacement = String((validator as any)[placeholder]);
        const re = new RegExp(escapeRegex(`{${placeholder}}`), 'g');
        message = message.replace(re, replacement);
    });
    return message;
};

// Remove used escape characters from the message (should be used as the last interpolation)
const interpolateRemoveEscapes: MessageProcessor = (message, _validator, _binderNode) => {
    return message.replace(/\\({.+?})/g, '$1');
};

/*
 * Return the first non-undefined result from the given interpolators.
 * Basically a prioritized list of alternative interpolators where the first valid result is used
 * and the rest don't need to be run
 */
const getFirstResult = (processors: MessageProcessorWithNullableResult[], defaultValue?: string) => {
    return ((message, validator, binderNode) => {
        for (const process of processors) {
            const result = process(message, validator, binderNode);
            if (typeof result === 'string') {
                return result;
            }
        }
        return defaultValue || message;
    }) as MessageProcessor;
};

// Translate all mesasge that start and end by {}
const translate: MessageProcessor = (message, _validator, _binderNode) => {
    console.log(message);
    if (message.startsWith("{") && message.endsWith("}")) {
        return get(message.slice(1,-1));
    } else {
        return message;
    }
};

const messageProcessors: MessageProcessor[] = [
    translate,
    getFirstResult([
        getMessageFromMap,
        getMessageByRegexMatch,
    ]),
    interpolateSize,
    interpolateFieldValue,
    interpolateValidatorProperties,
    interpolateRemoveEscapes,
];

const callback: typeof Binder.interpolateMessageCallback = (message, validator, binderNode) => {
    for (const interpolator of messageProcessors) {
        message = interpolator(message, validator, binderNode);
    }
    return message;
};

export const MessageInterpolator = {
    callback,
    setMessages(map: Map<string | RegExp, string>) {
        messages = map;
    }
};