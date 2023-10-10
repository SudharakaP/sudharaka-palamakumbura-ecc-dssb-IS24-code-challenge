import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { getSortState, JhiItemCount, JhiPagination, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from './product.reducer';
import { getEntities as getDevelopers } from 'app/entities/developer/developer.reducer';
import { getEntities as getScrumMasters } from 'app/entities/scrum-master/scrum-master.reducer';
import ReactSearchBox from 'react-search-box';
import { Input } from 'reactstrap';
import scrumMaster from 'app/entities/scrum-master/scrum-master.reducer';

export const Product = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [filterBy, setFilterBy] = useState({ key: 'SCRUM_MASTER', value: 'Scrum Master' });

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const productList = useAppSelector(state => state.product.entities);
  const developerList = useAppSelector(state => state.developer.entities);
  const scrumMasterList = useAppSelector(state => state.scrumMaster.entities);
  const loading = useAppSelector(state => state.product.loading);
  const totalItems = useAppSelector(state => state.product.totalItems);

  const getAllEntities = (scrumMasterId = '0', developerId = '0') => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
        scrumMasterId: scrumMasterId,
        developerId: developerId,
      })
    );

    dispatch(getDevelopers({}));
    dispatch(getScrumMasters({}));
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const filterOptions = [
    { key: 'SCRUM_MASTER', value: 'Scrum Master' },
    { key: 'DEVELOPER', value: 'Developer' },
  ];

  return (
    <div>
      <h2 id="product-heading" data-cy="ProductHeading">
        Products
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/product/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Product
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <div className="row pb-2">
          <div className="col-auto">
            <Input
              id="searchSelect"
              type="select"
              onChange={({ target }) => setFilterBy(filterOptions.find(element => element.value === target.value))}
            >
              {filterOptions.map(filterBy => (
                <option value={filterBy.value}>{filterBy.value}</option>
              ))}
            </Input>
          </div>
          <div className="col-auto">
            <ReactSearchBox
              data={
                filterBy.key === 'SCRUM_MASTER'
                  ? scrumMasterList.map(scrumMaster => ({ key: scrumMaster.id, value: scrumMaster.name }))
                  : developerList.map(developer => ({ key: developer.id, value: developer.name }))
              }
              onSelect={({ item }) => {
                filterBy.key === 'SCRUM_MASTER' ? getAllEntities(item.key, '0') : getAllEntities('0', item.key);
              }}
              onChange={() => {}}
              placeholder={`Filter by ${filterBy.value}`}
            />
          </div>
        </div>

        {productList && productList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productName')}>
                  Product Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startDate')}>
                  Start Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('methodology')}>
                  Methodology <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('location')}>
                  Location <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('scrumMaster')}>
                  Scrum Master <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productOwner')}>
                  Product Owner <FontAwesomeIcon icon="sort" />
                </th>
                <th>Developers</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productList.map((product, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/product/${product.id}`} color="link" size="sm">
                      {product.id}
                    </Button>
                  </td>
                  <td>{product.productName}</td>
                  <td>{product.startDate ? <TextFormat type="date" value={product.startDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{product.methodology}</td>
                  <td>{product.location}</td>
                  <td>
                    {product.scrumMaster ? <Link to={`/scrum-master/${product.scrumMaster.id}`}>{product.scrumMaster.name}</Link> : ''}
                  </td>
                  <td>
                    {product.productOwner ? <Link to={`/product-owner/${product.productOwner.id}`}>{product.productOwner.name}</Link> : ''}
                  </td>
                  <td>
                    {product.developers?.length > 0 ? (
                      <ul>
                        {product.developers.map(developer => {
                          return (
                            <li>
                              <Link to={`/developer/${developer.id}`}>{developer.name}</Link>
                            </li>
                          );
                        })}
                      </ul>
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/product/${product.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/product/${product.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/product/${product.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Products found</div>
        )}
      </div>
      {totalItems ? (
        <div className={productList && productList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Product;
