import React from 'react';
import { Route } from 'react-router-dom';
import Loadable from 'react-loadable';

import EntitiesRoutes from 'app/entities/routes';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import PageNotFound from 'app/shared/error/page-not-found';
import Product from 'app/entities/product';

const loading = <div>loading ...</div>;

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => loading,
});

const AppRoutes = () => {
  return (
    <div className="view-routes">
      <ErrorBoundaryRoutes>
        <Route index element={<Product />} />
        <Route path="admin/*" element={<Admin />} />
        <Route path="*" element={<EntitiesRoutes />} />
        <Route path="*" element={<PageNotFound />} />
      </ErrorBoundaryRoutes>
    </div>
  );
};

export default AppRoutes;
