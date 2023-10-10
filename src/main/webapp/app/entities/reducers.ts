import product from 'app/entities/product/product.reducer';
import developer from 'app/entities/developer/developer.reducer';
import scrumMaster from 'app/entities/scrum-master/scrum-master.reducer';
import productOwner from 'app/entities/product-owner/product-owner.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  product,
  developer,
  scrumMaster,
  productOwner,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
