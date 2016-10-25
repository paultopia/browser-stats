// extract data from everything in table form
function ARRAYDAMNIT(someArrayLikeObject) {
    return [].slice.call(someArrayLikeObject);
}

var tables = ARRAYDAMNIT(document.getElementsByTagName("table"));
var tabulardata = tables.map(extractTable);

function extractTable(table) {
    let rows = ARRAYDAMNIT(table.GetElementsByTagName("tr"));
    let headers = ARRAYDAMNIT(table.GetElementsByTagName("th"));
    return {"headers": headers, "rows": rows.map(extractRows)};
}

function extractRows(row) {
    let cells = ARRAYDAMNIT(row.GetElementsByTagName(""));
    return cells.map(extractContent);
}

function extractContent(cell) {
    return cell.innerText;
}

// a bit of testing
var dasdiv = document.createElement('div');
dasdiv.innerHTML = JSON.stringify(tabulardata);
