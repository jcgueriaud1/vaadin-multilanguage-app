import { Binder } from '@vaadin/form';
import { Router } from '@vaadin/router';
import { routes } from './routes';
import { appStore } from './stores/app-store';
import {get} from "lit-translate";
import { MessageInterpolator } from './localization';

Binder.interpolateMessageCallback = (message, validator, binderNode) => {

    if (message.startsWith("{") && message.endsWith("}")) {
        console.log("message" + message )
        console.log("get(message" + get(message.slice(1,-1)));
         return MessageInterpolator.callback(get(message.slice(1,-1)),validator, binderNode);
    } else {
        return message;
    }
};
export const router = new Router(document.querySelector('#outlet'));
router.setRoutes(routes);

window.addEventListener('vaadin-router-location-changed', (e) => {
  appStore.setLocation((e as CustomEvent).detail.location);
  const title = appStore.currentViewTitle;
  if (title) {
    document.title = title + ' | ' + appStore.applicationName;
  } else {
    document.title = appStore.applicationName;
  }
});
