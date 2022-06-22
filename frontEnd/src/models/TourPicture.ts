export class TourPicture{
  public id: string;
  public picture: string;
  public tourId: string;

  constructor(id: string, picture: string, tourId: string) {
    this.id = id;
    this.picture = picture;
    this.tourId = tourId;
  }
}
