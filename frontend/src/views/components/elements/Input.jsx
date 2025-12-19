export default function Input({ name, type, value, setValueFunc }) {
  return (
    <input id={ name }
      type={ type }
      name={ name }
      value={ value }
      onChange={ (e) => setValueFunc(e.target.value) }
      className="block min-w-0 grow bg-transparent py-1.5 pr-3 pl-1 text-base text-white placeholder:text-gray-500 focus:outline-none sm:text-sm/6" />
  )
}
