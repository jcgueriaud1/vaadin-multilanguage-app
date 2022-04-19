import '@vaadin/vaadin-app-layout';
import { AppLayoutElement } from '@vaadin/vaadin-app-layout';
import '@vaadin/vaadin-app-layout/vaadin-drawer-toggle';
import '@vaadin/vaadin-avatar/vaadin-avatar';
import '@vaadin/vaadin-tabs';
import '@vaadin/vaadin-tabs/vaadin-tab';
import { html } from 'lit';
import {customElement} from 'lit/decorators.js';
import { router } from '../../index';
import { views } from '../../routes';
import { appStore } from '../../stores/app-store';
import { Layout } from '../view';
import styles from './main-view.css';
import {registerTranslateConfig, use, translate, extract} from "lit-translate";
import {getTranslations} from "Frontend/generated/TranslationEndpoint";
import {ITranslateConfig, Values, ValuesCallback} from "lit-translate/model";

interface RouteInfo {
  path: string;
  title: string;
}

@customElement('main-view')
export class MainView extends Layout {
  static get styles() {
    return [styles];
  }

  render() {
    return html`
      <vaadin-app-layout primary-section="drawer">
        <header slot="navbar" theme="dark">
          <vaadin-drawer-toggle></vaadin-drawer-toggle>
          <h1>${appStore.currentViewTitle}</h1>
          <vaadin-avatar></vaadin-avatar>
        </header>

        <div slot="drawer">
          <div id="logo">
            <img src="images/logo.png" alt="${appStore.applicationName} logo" />
            <span>${appStore.applicationName}</span>
          </div>

          <div>
            <vaadin-button @click="${this.switchToEnglish}">English</vaadin-button>
            <vaadin-button @click="${this.switchToFrench}">Francais</vaadin-button>
          </div>
          <hr />
          <vaadin-tabs orientation="vertical" theme="minimal" .selected=${this.getSelectedViewRoute()}>
            ${this.getMenuRoutes().map(
              (viewRoute) => html`
                <vaadin-tab>
                  <a href="${router.urlForPath(viewRoute.path)}" tabindex="-1">${translate(viewRoute.title)}</a>
                </vaadin-tab>
              `
            )}
          </vaadin-tabs>
        </div>
        <slot></slot>
      </vaadin-app-layout>
    `;
  }

  connectedCallback() {
    super.connectedCallback();
    registerTranslateConfig({
      loader: lang => getTranslations(lang).then(res => JSON.parse(res!)),
    interpolate: this.interpolate
    });
    use("en_GB");
    this.reaction(
      () => appStore.location,
      () => {
        AppLayoutElement.dispatchCloseOverlayDrawerEvent();
      }
    );
  }

  /*
    Use only 1 { instead of 2
   */
  private interpolate(text: string, values: Values | ValuesCallback | null, config: ITranslateConfig): string {
    return Object.entries(extract(values || {}))
        .reduce((text, [key, value]) => text.replace(new RegExp(`{[  ]*${key}[  ]*}`, `gm`), String(extract(value))), text);
  }

  private getMenuRoutes(): RouteInfo[] {
    return [
      {
        path: 'hello',
        title: 'fusion.form.title',
      },

      {
        path: 'about',
        title: 'fusion.aboutview.title',
      },

      {
        path: 'form-flow',
        title: 'flow.form.title',
      },

      {
        path: 'about-flow',
        title: 'flow.aboutview.title',
      },
    ];

    return views.filter((route) => route.title) as RouteInfo[];
  }

  private getSelectedViewRoute(): number {
    return this.getMenuRoutes().findIndex((viewRoute) => viewRoute.path == appStore.location);
  }

  switchToFrench() {
    use("fr_FR");
  }

  switchToEnglish() {
    use("en_GB");
  }
}
