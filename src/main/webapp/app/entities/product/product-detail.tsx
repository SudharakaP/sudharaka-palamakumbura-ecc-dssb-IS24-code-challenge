import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">Product</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="productName">Product Name</span>
          </dt>
          <dd>{productEntity.productName}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>
            {productEntity.startDate ? <TextFormat value={productEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="methodology">Methodology</span>
          </dt>
          <dd>{productEntity.methodology}</dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{productEntity.location}</dd>
          <dt>Developer</dt>
          <dd>
            {productEntity.developers
              ? productEntity.developers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {productEntity.developers && i === productEntity.developers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Scrum Master</dt>
          <dd>{productEntity.scrumMaster ? productEntity.scrumMaster.id : ''}</dd>
          <dt>Product Owner</dt>
          <dd>{productEntity.productOwner ? productEntity.productOwner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
