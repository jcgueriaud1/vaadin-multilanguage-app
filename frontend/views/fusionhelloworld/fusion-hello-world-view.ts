import '!style-loader!css-loader!./fusion-hello-world-view.css';
import { showNotification } from '@vaadin/flow-frontend/a-notification';
import '@vaadin/vaadin-button';
import '@vaadin/vaadin-text-field';
import { customElement, html } from 'lit-element';
import { View } from '../../views/view';
import {Binder, field} from '@vaadin/form';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';

import * as PersonEndpoint from 'Frontend/generated/PersonEndpoint';
import PersonModel from 'Frontend/generated/org/vaadin/jchristophe/application/data/PersonModel';
import {translate} from "lit-translate";

@customElement('fusion-hello-world-view')
export class FusionHelloWorldView extends View {
  name: string = '';

  private binder = new Binder(this, PersonModel);

  render() {
    return html`
      <vaadin-vertical-layout>
      <vaadin-text-field
          label="${translate('form.person.firstName')}"
          ...="${field(this.binder.model.firstName)}"
      ></vaadin-text-field>
      <vaadin-text-field
          label="${translate('form.person.lastName')}"
          ...="${field(this.binder.model.lastName)}"
      ></vaadin-text-field>
      <vaadin-text-field
          label="${translate('form.person.email')}"
          ...="${field(this.binder.model.email)}"
      ></vaadin-text-field>
      <vaadin-button @click="${this.savePerson}">${translate('form.validate')}</vaadin-button>
      </vaadin-vertical-layout>
    `;
  }
  nameChanged(e: CustomEvent) {
    this.name = e.detail.value;
  }

  async savePerson() {
    await this.binder.submitTo(PersonEndpoint.savePerson);
    showNotification(`Person saved`);
  }
}
