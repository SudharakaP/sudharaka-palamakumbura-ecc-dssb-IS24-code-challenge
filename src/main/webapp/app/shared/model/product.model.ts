import dayjs from 'dayjs';
import { IDeveloper } from 'app/shared/model/developer.model';
import { IScrumMaster } from 'app/shared/model/scrum-master.model';
import { IProductOwner } from 'app/shared/model/product-owner.model';
import { Methodology } from 'app/shared/model/enumerations/methodology.model';

export interface IProduct {
  id?: number;
  productName?: string | null;
  startDate?: string | null;
  methodology?: Methodology | null;
  location?: string | null;
  developers?: IDeveloper[] | null;
  scrumMaster?: IScrumMaster | null;
  productOwner?: IProductOwner | null;
}

export const defaultValue: Readonly<IProduct> = {};
