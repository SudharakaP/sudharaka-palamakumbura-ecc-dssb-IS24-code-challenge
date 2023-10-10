import { IProduct } from 'app/shared/model/product.model';

export interface IScrumMaster {
  id?: number;
  name?: string | null;
  products?: IProduct[] | null;
}

export const defaultValue: Readonly<IScrumMaster> = {};
