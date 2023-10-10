import product from 'app/entities/product/product.reducer';
import developer from 'app/entities/developer/developer.reducer';
import scrumMaster from 'app/entities/scrum-master/scrum-master.reducer';
import productOwner from 'app/entities/product-owner/product-owner.reducer';

const entitiesReducers = {
  product,
  developer,
  scrumMaster,
  productOwner,
};

export default entitiesReducers;
