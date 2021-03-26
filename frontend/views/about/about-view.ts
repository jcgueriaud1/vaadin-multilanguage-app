import '!style-loader!css-loader!./about-view.css';
import { customElement, html } from 'lit-element';
import { View } from '../../views/view';

@customElement('about-view')
export class AboutView extends View {
  render() {
    return html`<div>Content placeholder</div>`;
  }
}
