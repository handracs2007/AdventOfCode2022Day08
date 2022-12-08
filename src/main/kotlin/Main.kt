import java.io.File

fun part1(trees: List<List<Int>>) {
    var visible = 0

    for (row in trees.indices) {
        val rowTrees = trees[row]

        for (col in rowTrees.indices) {
            val tree = rowTrees[col]

            if ((row == 0 || row == trees.size - 1 || col == 0 || col == rowTrees.size - 1)) {
                // If it's at the edge then it is visible.
                visible++
                continue
            }

            // Check all the way to the left. It's guaranteed that this tree has left side due to above validation.
            var notVisible = false
            for (c in 0 until col) {
                if (trees[row][c] >= tree) {
                    // There is a higher tree that blocks the view to this tree.
                    notVisible = true
                    break
                }
            }

            if (!notVisible) {
                visible++
                continue
            }

            // Check all the way to the top. It's guaranteed that this tree has top side due to above validation.
            notVisible = false
            for (r in 0 until row) {
                if (trees[r][col] >= tree) {
                    // There is a higher tree that blocks the view to this tree.
                    notVisible = true
                    break
                }
            }

            if (!notVisible) {
                visible++
                continue
            }

            // Check all the way to the right. It's guaranteed that this tree has right side due to above validation.
            notVisible = false
            for (c in col + 1 until rowTrees.size) {
                if (trees[row][c] >= tree) {
                    // There is a higher tree that blocks the view to this tree.
                    notVisible = true
                    break
                }
            }

            if (!notVisible) {
                visible++
                continue
            }

            // Check all the way down down. It's guaranteed that this tree has down side due to above validation.
            notVisible = false
            for (r in row + 1 until trees.size) {
                if (trees[r][col] >= tree) {
                    // There is a higher tree that blocks the view to this tree.
                    notVisible = true
                    break
                }
            }

            if (!notVisible) {
                visible++
            }
        }
    }

    println(visible)
}

fun part2(trees: List<List<Int>>) {
    var score = 1
    var maxScore = 0

    // We ignore the edge, because based on the formula, it will be always 0.
    for (row in 1 until trees.size - 1) {
        val rowTrees = trees[row]

        // We ignore the edge, because based on the formula, it will always be 0.
        for (col in 1 until rowTrees.size - 1) {
            score = 1
            val tree = rowTrees[col]

            // Check viewing all the way to the top.
            var visible = 0
            for (r in row.downTo(1)) {
                val top = trees[r - 1][col]
                val down = trees[r][col]

                if (r == row) {
                    visible++
                } else if (top > down || top < tree) {
                    visible++
                }

                // We should not check further if the tree has the same height or higher than the current tree.
                if (top >= tree)
                    break
            }

            score *= visible

            // Check viewing all the way to the left.
            visible = 0
            for (c in col.downTo(1)) {
                val left = trees[row][c - 1]
                val right = trees[row][c]

                if (c == col) {
                    visible++
                } else if (left > right || left < tree) {
                    visible++
                }

                // We should not check further if the tree has the same height or higher than the current tree.
                if (left >= tree)
                    break
            }

            score *= visible

            // Check viewing all the way to the below.
            visible = 0
            for (r in row until trees.size - 1) {
                val top = trees[r][col]
                val down = trees[r + 1][col]

                if (r == row) {
                    visible++
                } else if (down > top || down < tree) {
                    visible++
                }

                // We should not check further if the tree has the same height or higher than the current tree.
                if (down >= tree)
                    break
            }

            score *= visible

            // Check viewing all the way to the right.
            visible = 0
            for (c in col until rowTrees.size - 1) {
                val left = trees[row][c]
                val right = trees[row][c + 1]

                if (c == col) {
                    visible++
                } else if (right > left || right < tree) {
                    visible++
                }

                // We should not check further if the tree has the same height or higher than the current tree.
                if (right >= tree)
                    break
            }

            score *= visible

            if (score > maxScore) {
                maxScore = score
            }
        }
    }

    println(maxScore)
}

fun main() {
    val trees = mutableListOf<List<Int>>()

    // We read the whole content and store it into a 2-dimensional list.
    File("input.txt").forEachLine { line -> trees.add(line.map { chr -> chr.digitToInt() }) }

    part1(trees)
    part2(trees)
}