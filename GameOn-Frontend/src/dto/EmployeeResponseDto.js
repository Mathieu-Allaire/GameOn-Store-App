export class EmployeeResponseDto {
  constructor(response) {
    this.email = response.email;
    this.name = response.name;
    this.isEmployed = response.isEmployed;
  }
}
