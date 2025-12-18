import { Component } from "react";

export default class SignInForm extends Component {
  constructor(props) {
    super(props)
    this.state = {
      email: '',
      password: '',
    };
    this.handleInputChange = this.handleInputChange.bind(this);
  }

  handleInputChange(event) {
    const name = event.target.name;
    const value = event.target.value;
    this.setState( { [name] : value } );
  }

  handleSubmit(event) {
    if (!this.email || !this.password) {
      // todo validate, maybe callback?
    }
    // send to server
    event.preventDefault();
  }

  render() {
    return (
      <form onSubmit={ this.handleSubmit }>
        <div className="mb-2">
          <label htmlFor="email" className="block text-sm/6 font-medium text-white">Email</label>
          <div className="mt-2">
            <div className="flex items-center rounded-md bg-white/5 pl-3 outline-1 -outline-offset-1 outline-white/10 focus-within:outline-2 focus-within:-outline-offset-2 focus-within:outline-indigo-500">
              <input id="email"
                type="text"
                name="email"
                value={ this.state.email }
                onChange={ this.handleInputChange }
                className="block min-w-0 grow bg-transparent py-1.5 pr-3 pl-1 text-base text-white placeholder:text-gray-500 focus:outline-none sm:text-sm/6" />
            </div>
          </div>
        </div>

        <div className="mb-2">
          <label htmlFor="password" className="block text-sm/6 font-medium text-white">Password</label>
          <div className="mt-2">
            <div className="flex items-center rounded-md bg-white/5 pl-3 outline-1 -outline-offset-1 outline-white/10 focus-within:outline-2 focus-within:-outline-offset-2 focus-within:outline-indigo-500">
              <input id="password"
                type="password"
                name="password"
                value={ this.state.password }
                onChange={ this.handleInputChange }
                className="block min-w-0 grow bg-transparent py-1.5 pr-3 pl-1 text-base text-white placeholder:text-gray-500 focus:outline-none sm:text-sm/6" />
            </div>
          </div>
        </div>

        <div className="mt-6 flex items-center justify-end gap-x-6">
          <button type="button" className="text-sm/6 font-semibold text-white">
            Sign up
          </button>
          <button
            type="submit"
            className="rounded-md bg-indigo-500 px-3 py-2 text-sm font-semibold text-white focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500"
          >
            Save
          </button>
        </div>
      </form>
    );
  }
}
