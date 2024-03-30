import {AddressModel} from "./address-model";
import {PhoneNumberModel} from "./phoneNumber-model";

export interface AppUserModel {
  id: number,
  name: string,
  email: string,
  motherName: string,
  birthDate: string,
  socialSecurityNumber: string,
  taxIdentificationNumber: string,
  rolesList: string[],
  addressDetailsDtosList: AddressModel[],
  phoneNumberDetailsDtoList: PhoneNumberModel[]
}
