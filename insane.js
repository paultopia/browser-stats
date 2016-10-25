// EXPERIMENTAL AND UNSAFE hack to fix array-like objects

Object.prototype.pgmap = function(f) {
    return [].slice.call(this).map(f);
}

goofy = document.getElementsByTagName("table").pgmap(extractTable);

function ARRAYDAMNIT(someArrayLikeObject) {
    return [].slice.call(someArrayLikeObject);
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

console.log(JSON.stringify(goofy));
