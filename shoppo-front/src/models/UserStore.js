export default class UserStore {
  constructor(token, role, user) {
    this.token = token;
    this.role = role;
    this.user = user;
  }

  printUser() {
    console.log(this.token);
  }
}
