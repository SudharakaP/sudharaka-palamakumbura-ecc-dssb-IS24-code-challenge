import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Product from './product';
import Developer from './developer';
import ScrumMaster from './scrum-master';
import ProductOwner from './product-owner';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="product/*" element={<Product />} />
        <Route path="developer/*" element={<Developer />} />
        <Route path="scrum-master/*" element={<ScrumMaster />} />
        <Route path="product-owner/*" element={<ProductOwner />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
