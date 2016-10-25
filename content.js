function ARRAYDAMNIT(someArrayLikeObject) {
    return [].slice.call(someArrayLikeObject);
}

// extract data from everything in table form. Verified to work for headers in thead.

function extractTabularData() {
    return ARRAYDAMNIT(document.getElementsByTagName("table")).map(extractTable);
}

function extractTable(table) {
    return ARRAYDAMNIT(table.rows).map(extractRows);
}

function extractRows(row) {
    return ARRAYDAMNIT(row.cells).map(extractContent);
}

function extractContent(cell) {
    return cell.innerText;
}

// gather all the data and send it somewhere
allData = {"tabular": extractTabularData()};

console.log(JSON.stringify(allData));
