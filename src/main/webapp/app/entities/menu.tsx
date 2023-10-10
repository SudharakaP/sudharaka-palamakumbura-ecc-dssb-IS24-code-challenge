import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/developer">
        Developer
      </MenuItem>
      <MenuItem icon="asterisk" to="/scrum-master">
        Scrum Master
      </MenuItem>
      <MenuItem icon="asterisk" to="/product-owner">
        Product Owner
      </MenuItem>
    </>
  );
};

export default EntitiesMenu;
