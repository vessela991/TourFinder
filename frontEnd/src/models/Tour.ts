export class Tour{
  public id: string;
  public name: string;
  public destination: string;
  public description: string;
  public price: number;
  public startDate: Date;
  public endDate: Date;
  public agencyName: string;

  constructor(id: string, name: string, destination: string, description: string, price: number, startDate: Date,
              endDate: Date, agencyName: string) {
    this.id = id;
    this.name = name;
    this.destination = destination;
    this.description = description;
    this.price = price;
    this.startDate = startDate;
    this.endDate = endDate;
    this.agencyName = agencyName;
  }
}
