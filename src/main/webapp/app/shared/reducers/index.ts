import { ReducersMapObject } from '@reduxjs/toolkit';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import entitiesReducers from 'app/entities/reducers';

const rootReducer: ReducersMapObject = {
  applicationProfile,
  administration,
  loadingBar,
  ...entitiesReducers,
};

export default rootReducer;
