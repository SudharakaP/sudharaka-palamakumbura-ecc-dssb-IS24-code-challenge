import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getDevelopers } from 'app/entities/developer/developer.reducer';
import { getEntities as getScrumMasters } from 'app/entities/scrum-master/scrum-master.reducer';
import { getEntities as getProductOwners } from 'app/entities/product-owner/product-owner.reducer';
import { Methodology } from 'app/shared/model/enumerations/methodology.model';
import { createEntity, getEntity, reset, updateEntity } from './product.reducer';

export const ProductUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const developers = useAppSelector(state => state.developer.entities);
  const scrumMasters = useAppSelector(state => state.scrumMaster.entities);
  const productOwners = useAppSelector(state => state.productOwner.entities);
  const productEntity = useAppSelector(state => state.product.entity);
  const loading = useAppSelector(state => state.product.loading);
  const updating = useAppSelector(state => state.product.updating);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);
  const methodologyValues = Object.keys(Methodology);

  const handleClose = () => {
    navigate('/product' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDevelopers({}));
    dispatch(getScrumMasters({}));
    dispatch(getProductOwners({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...productEntity,
      ...values,
      developers: mapIdList(values.developers),
      scrumMaster: scrumMasters.find(it => it.id.toString() === values.scrumMaster.toString()),
      productOwner: productOwners.find(it => it.id.toString() === values.productOwner.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          methodology: 'WATERFALL',
          ...productEntity,
          developers: productEntity?.developers?.map(e => e.id.toString()),
          scrumMaster: productEntity?.scrumMaster?.id,
          productOwner: productEntity?.productOwner?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="codeChallengeApp.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            Create or edit a Product
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="product-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Product Name" id="product-productName" name="productName" data-cy="productName" type="text" />
              <ValidatedField label="Start Date" id="product-startDate" name="startDate" data-cy="startDate" type="date" />
              <ValidatedField label="Methodology" id="product-methodology" name="methodology" data-cy="methodology" type="select">
                {methodologyValues.map(methodology => (
                  <option value={methodology} key={methodology}>
                    {methodology}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Location" id="product-location" name="location" data-cy="location" type="text" />
              <ValidatedField label="Developer" id="product-developer" data-cy="developer" type="select" multiple name="developers">
                <option value="" key="0" />
                {developers
                  ? developers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="product-scrumMaster" name="scrumMaster" data-cy="scrumMaster" label="Scrum Master" type="select">
                <option value="" key="0" />
                {scrumMasters
                  ? scrumMasters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="product-productOwner" name="productOwner" data-cy="productOwner" label="Product Owner" type="select">
                <option value="" key="0" />
                {productOwners
                  ? productOwners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ProductUpdate;
