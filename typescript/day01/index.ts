const fs = require("fs")

var content = fs.readFileSync("./input1.txt", "utf8").toString().split("\n")

let sum = 0;
for (const lineIndex in content) {
    let num1 = 0, num2 = 0;
    const strlen = content[lineIndex].length
    for (let i = 0; i < strlen; i++) {
        const valuefront = content[lineIndex][i]
        const valuelast = content[lineIndex][strlen - i]
        if (!isNaN(valuefront) && num1 === 0) {
            num1 = valuefront;
        }
        if (!isNaN(valuelast) && num2 === 0) {
            num2 = valuelast
        }
    }
    if (num2 === 0) num2 = num1

    sum += Number(num1 + num2)
}
console.log(sum)

