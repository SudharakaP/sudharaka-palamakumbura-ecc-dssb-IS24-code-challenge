import React from 'react';
import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';

import initStore from 'app/config/store';
import Header from './header';

describe('Header', () => {
  let mountedWrapper;
  const devProps = {
    isAuthenticated: true,
    isAdmin: true,
    ribbonEnv: 'dev',
    isInProduction: false,
    isOpenAPIEnabled: true,
  };
  const prodProps = {
    ...devProps,
    ribbonEnv: 'prod',
    isInProduction: true,
    isOpenAPIEnabled: false,
  };
  const userProps = {
    ...prodProps,
    isAdmin: false,
  };
  const guestProps = {
    ...prodProps,
    isAdmin: false,
    isAuthenticated: false,
  };

  const wrapper = (props = devProps) => {
    if (!mountedWrapper) {
      const store = initStore();
      const { container } = render(
        <Provider store={store}>
          <MemoryRouter>
            <Header {...props} />
          </MemoryRouter>
        </Provider>
      );
      mountedWrapper = container.innerHTML;
    }
    return mountedWrapper;
  };

  beforeEach(() => {
    mountedWrapper = undefined;
  });

  // All tests will go here
  it('Renders a Header component in dev profile with LoadingBar, Navbar, Nav and dev ribbon.', () => {
    const html = wrapper();

    // Find Navbar component
    expect(html).toContain('navbar');
    // Find EntitiesMenu component
    expect(html).toContain('entity-menu');
    // Ribbon
    expect(html).toContain('ribbon');
  });
});
