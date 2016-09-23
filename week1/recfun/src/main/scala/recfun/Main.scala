package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if(c == 0) 1
      else if (c==r) 1
      else pascal(c-1,r-1) + pascal(c,r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      countBalance(0,chars) == 0
    }
    
    def countBalance(open: Int, chars: List[Char]): Int = {
      if (chars.isEmpty) open
      else if (open == -1) -1
      else if (chars.head == '(') countBalance(open+1, chars.tail)
      else if (chars.head == ')') countBalance(open-1, chars.tail)
      else countBalance(open,chars.tail)
    }

  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if(money < 0 || coins.isEmpty) 0
      else if (money == 0) 1
      else countChange(money, coins.tail) + countChange(money-coins.head, coins)
    }

  }
