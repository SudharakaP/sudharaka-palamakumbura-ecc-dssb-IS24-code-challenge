import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './scrum-master.reducer';

export const ScrumMasterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const scrumMasterEntity = useAppSelector(state => state.scrumMaster.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="scrumMasterDetailsHeading">Scrum Master</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{scrumMasterEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{scrumMasterEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/scrum-master" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scrum-master/${scrumMasterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ScrumMasterDetail;
