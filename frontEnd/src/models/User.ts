import {Role} from "./Role";

export class User{
  public id: string;
  public username: string;
  public password: string;
  public email: string;
  public phoneNumber: string;
  public role: Role;
  public bookedTours: Array<string>;

  constructor(id:string, username: string, password: string, email: string, phoneNumber: string,
              role:Role, bookedTours: Array<string>) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.bookedTours = bookedTours;
  }

}
