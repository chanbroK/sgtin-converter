const prefixList = require('./gcpprefixformatlist.json');

const getSGTIN = (gtin, maxLength) => {
  if(gtin.length == 8) return computeSGTINForGTIN8(gtin);
  // Add padding
  let gtin14 = convertToGTIN14(gtin);
  let gcpLength = getGCPLength(gtin14);
  let companyPrefix = gtin14.substring(1, gcpLength+1);
  let indicator = gtin14[0];
  let itemRef = gtin14.substring(gcpLength+1, 13);

  return formatSGTIN(companyPrefix, indicator, itemRef);
}

const formatSGTIN = (companyPrefix, indicator, itemRef) => {
  return `urn:epc:id:sgtin:${companyPrefix}.${indicator}${itemRef}.*`;
}

const computeSGTINForGTIN8 = (gtin8) => {
  let companyPrefix = "0".repeat(5) + gtin8.substring(0,3);
  let indicator = "0";
  let itemRef = gtin8.substring(3, 7);

  return formatSGTIN(companyPrefix, indicator, itemRef);
}

const getJSON = () => {
  let max = 0
  for(let entry of prefixList["GCPPrefixFormatList"]["entry"]){
    if (entry["prefix"].length > max) max = entry["prefix"].length;
  }

  return max;
}

const convertToGTIN14 = (gtin) => {
  let padding = 14 - gtin.length;

  return "0".repeat(padding) + gtin;
}


const getGCPLength = (gtin14) => {
  for(let i = gtin14.length; i > 0; i--){
    let gtinSubstring = gtin14.substring(1, i)
    for(let entry of prefixList["GCPPrefixFormatList"]["entry"]){
      if(entry["prefix"] == gtinSubstring) return entry["gcpLength"];
    }
  }
}

module.exports = {
  getSGTIN,
}
