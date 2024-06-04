import {
  MissingTranslationHandler,
  MissingTranslationHandlerParams
} from '@ngx-translate/core';

export class DefaultTranslationHandler implements MissingTranslationHandler {
  handle(params: MissingTranslationHandlerParams): unknown {
    if (params.interpolateParams) {
      return (
        (params.interpolateParams as { [key: string]: unknown })['default'] ||
        params.key
      );
    }
    return params.key;
  }
}
