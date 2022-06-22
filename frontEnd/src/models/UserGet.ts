import {Role} from "./Role";

export class UserGet{
  public username: string;
  public email: string;
  public phoneNumber: string;
  public role: Role;
  public bookedTours: Array<string>;

  constructor(username:string, email:string, phoneNumber:string, role:Role, bookedTours: Array<string>) {
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.bookedTours = bookedTours;
  }

}
