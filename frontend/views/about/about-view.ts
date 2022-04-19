import '!style-loader!css-loader!./about-view.css';
import { html } from 'lit';
import {customElement} from 'lit/decorators.js';
import { View } from '../../views/view';
import {translate } from "lit-translate";

@customElement('about-view')
export class AboutView extends View {
  render() {
    return html`<div>${translate('aboutview.text')}</div>
        <div>${translate('aboutview.textWithParameter', { '0': () => 1 })}</div>`;
  }
}
