import java.util.Stack;

public class Parser{

    private StringBuilder    repeat(StringBuilder src, int n)
    {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++)
            res.append(src);
        return res;
    }


    /**
     *  В  Стеке operators храним числа и знак +
     *  число значит насколько нужно умножить содержимое внутренних скобок
     *  + добавляем если есть символы вне внутренних скобок
     *  В Стеке tokens храним подстроки
     */
    public String parse(String source)
    {
        Stack<String> operators = new Stack<>();
        Stack<StringBuilder> tokens = new Stack<>();
        for (int i = 0; i < source.length(); i++)
        {
            if (!Character.isLetterOrDigit(source.charAt(i)) && source.charAt(i) != '[' && source.charAt(i) != ']')
                return null;
            if (Character.isDigit(source.charAt(i)))
            {
                if (i != 0 && Character.isLetter(source.charAt(i - 1)))
                    operators.push("+");
                StringBuilder sb = new StringBuilder();
                while (i < source.length() && Character.isDigit(source.charAt(i)))
                    sb.append(source.charAt(i++));
                if (i == source.length() || source.charAt(i) != '[')
                    return null;
                operators.push(sb.toString());
                i--;
            }
            else if (source.charAt(i) == '[' && (i == 0 || !Character.isDigit(source.charAt(i - 1))))
                return null;
            else if (Character.isLetter(source.charAt(i)) && (i == 0 || source.charAt(i - 1) == '['))
                tokens.push(new StringBuilder().append(source.charAt(i)));
            else if (Character.isLetter(source.charAt(i)) && source.charAt(i - 1) != '[')
                tokens.peek().append(source.charAt(i));
            else if (source.charAt(i) == ']') {
                if (operators.empty())
                    return null;
                StringBuilder last = tokens.pop();
                StringBuilder rep = repeat(last, Integer.parseInt(operators.pop()));
                if (!operators.empty() && operators.peek().equals("+")) {
                    operators.pop();
                    if (tokens.empty())  //----> где-то пустое содержимое скобок
                        return null;
                    tokens.push(tokens.pop().append(rep));
                }
                else
                    tokens.push(rep);
                if (i != source.length() - 1 && Character.isDigit(source.charAt(i + 1)))
                    operators.push("+");
            }
        }
        if (!operators.empty())
            return null;
        return tokens.pop().toString();
    }

    public static void main(String[] args) {
        if (args.length != 1)
            System.out.println("Wrong numbers of arguments");
        else
            System.out.println(new Parser().parse(args[0]));
    }
}
