import {AbstractModel, Binder, BinderConfiguration, FieldStrategy, ModelConstructor} from '@vaadin/form';
import { MyCcStrategy } from './my-cc-strategy';

export class MyBinder<T, M extends AbstractModel<T>> extends Binder<T, M> {
    constructor(context: any, model: ModelConstructor<T, M>, config?: BinderConfiguration<T>) {
        super(context, model, config);
    }

    getFieldStrategy(element: any): FieldStrategy {
        if (element.localName === 'my-cc-field') {
            return new MyCcStrategy(element);
        }
        return super.getFieldStrategy(element);
    }
}