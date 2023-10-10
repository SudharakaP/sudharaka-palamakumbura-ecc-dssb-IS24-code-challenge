import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Developer from './developer';
import DeveloperDetail from './developer-detail';
import DeveloperUpdate from './developer-update';
import DeveloperDeleteDialog from './developer-delete-dialog';

const DeveloperRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Developer />} />
    <Route path="new" element={<DeveloperUpdate />} />
    <Route path=":id">
      <Route index element={<DeveloperDetail />} />
      <Route path="edit" element={<DeveloperUpdate />} />
      <Route path="delete" element={<DeveloperDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DeveloperRoutes;
