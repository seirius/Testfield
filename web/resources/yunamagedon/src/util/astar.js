var astar = {
    init: function (grid) {
        for (var x = 0; x < grid.length; x++) {
            for (var y = 0; y < grid[x].length; y++) {
                var cell = grid[x][y];
                cell.f = null;
                cell.g = null;
                cell.h = null;
                cell.debug = null;
                cell.parent = null;
            }
        }
    },
    search: function (grid, start, end) {
        astar.init(grid);
        
        var openList = [];
        var closedList = [];
        openList.push(start);
        
        while (openList.length > 0) {
            var lowInd = 0;
            for (var i = 0; i < openList.length; i++) {
                if (openList[i].f < openList[lowInd].f) {
                    lowInd = i;
                }
            }
            
            var currentNode = openList[lowInd];
            
            if (currentNode.pos === end.pos) {
                var curr = currentNode;
                var ret = [];
                while (curr.parent) {
                    ret.push(curr);
                    curr = curr.parent;
                }
                
                return ret.reverse();
            }
            
            openList.removeGraphNode(currentNode);
            closedList.push(currentNode);
            var neighbors = astar.neighbors(grid, currentNode);
            
            for (var i = 0; i < neighbors.length; i++) {
                var neighbor = neighbors[i];
                if (closedList.findGraphNode(neighbor) || neighbor.isWall()) {
                    continue;
                }
                
                var gScore = currentNode.g + 1;
                var gScoreIsBest = false;
                
                if (!openList.findGraphNode(neighbor)) {
                    gScoreIsBest = true;
                    neighbor.h = astar.heuristic(neighbor.pos, end.pos);
                    openList.push(neighbor);
                } else if (gScore < neighbor.g) {
                    gScoreIsBest = true;
                }
                
                if (gScoreIsBest) {
                    neighbor.parent = currentNode;
                    neighbor.g = gScore;
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.debug = "F: " + neighbor.f + "<br/>G: " + neighbor.g + "<br>H: " + neighbor.h;
                }
            }
        }
    },
    heuristic: function (pos0, pos1) {
        var d1 = Math.abs(pos1.x - pos0.x);
        var d2 = Math.abs(pos1.y - pos0.y);
        return d1 + d2;
    },
    neighbors: function (grid, node) {
        var ret = [];
        var x = node.pos.x;
        var y = node.pos.y;
        
        if (grid[x - 1] && grid[x - 1][y]) {
            ret.push(grid[x - 1][y]);
        }
        if (grid[x + 1] && grid[x + 1][y]) {
            ret.push(grid[x + 1][y]);
        }
        if (grid[x] && grid[x][y - 1]) {
            ret.push(grid[x][y - 1]);
        }
        if (grid[x] && grid[x][y + 1]) {
            ret.push(grid[x][y + 1]);
        }
        
        return ret;
    }
};

