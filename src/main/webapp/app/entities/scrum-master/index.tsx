import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ScrumMaster from './scrum-master';
import ScrumMasterDetail from './scrum-master-detail';
import ScrumMasterUpdate from './scrum-master-update';
import ScrumMasterDeleteDialog from './scrum-master-delete-dialog';

const ScrumMasterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ScrumMaster />} />
    <Route path="new" element={<ScrumMasterUpdate />} />
    <Route path=":id">
      <Route index element={<ScrumMasterDetail />} />
      <Route path="edit" element={<ScrumMasterUpdate />} />
      <Route path="delete" element={<ScrumMasterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ScrumMasterRoutes;
