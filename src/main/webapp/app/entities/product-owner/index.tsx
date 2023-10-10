import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProductOwner from './product-owner';
import ProductOwnerDetail from './product-owner-detail';
import ProductOwnerUpdate from './product-owner-update';
import ProductOwnerDeleteDialog from './product-owner-delete-dialog';

const ProductOwnerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProductOwner />} />
    <Route path="new" element={<ProductOwnerUpdate />} />
    <Route path=":id">
      <Route index element={<ProductOwnerDetail />} />
      <Route path="edit" element={<ProductOwnerUpdate />} />
      <Route path="delete" element={<ProductOwnerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProductOwnerRoutes;
