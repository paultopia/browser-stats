function ARRAYDAMNIT(someArrayLikeObject) {
    return [].slice.call(someArrayLikeObject);
}

// extract data from everything in table form. Verified to work for headers in thead.


var tables = ARRAYDAMNIT(document.getElementsByTagName("table"));
var tabulardata = tables.map(extractTable);

function extractTable(table) {
    let rows = ARRAYDAMNIT(table.rows);
    return rows.map(extractRows);
}

function extractRows(row) {
    let cells = ARRAYDAMNIT(row.cells);
    return cells.map(extractContent);
}

function extractContent(cell) {
    return cell.innerText;
}

console.log(JSON.stringify(tabulardata));
