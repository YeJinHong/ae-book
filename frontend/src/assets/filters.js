const shortText = (text, length, suffix) => {
  if (text.length > length) {
    return text.substring(0, length) + suffix
  } else {
    return text
  }
}

const pricePoint = value => {
  return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
}

export { shortText, pricePoint }
