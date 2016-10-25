var ARRAYDAMNIT = function(someArrayLikeObject) {
    return [].slice.call(someArrayLikeObject);
}

Object.prototype.map = function(f) {
    return ARRAYDAMNIT(this).map(f);
}

// extract data from everything in table form. Verified to work for headers in thead.
function extractTabularData() {
    return document.getElementsByTagName("table").map(extractTable);
}

function extractTable(table) {
    return table.rows.map(extractRows);
}

function extractRows(row) {
    return row.cells.map(extractContent);
}

function extractContent(cell) {
    return cell.innerText;
}

// gather all the data and send it somewhere
allData = {"tabular": extractTabularData(), "html": document.documentElement.innerHTML, "text": document.documentElement.innerText};

console.log(JSON.stringify(allData));
