package com.example.gauravsehar.firstappgs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int[] Buttons = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.buttonAdd,
            R.id.buttonSub, R.id.buttonMul, R.id.buttonDiv, R.id.buttonDot, R.id.buttonClear, R.id.buttonBksp, R.id.buttonEqual};
    private TextView editorTextView;
    private TextView historyTextView;
    private TextView resultTextView;
    private boolean isLastDot = false;
    private boolean isLastOp = false;
    private boolean isNumSigned = false;
    private boolean isLastNumber = false;
    private double number = 0;
    private double result = 0;
    private boolean isEqualPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            for (int id : Buttons)
                findViewById(id).setOnClickListener(this);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Exception Occur", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        editorTextView = findViewById(R.id.textViewEquation);
        historyTextView = findViewById(R.id.textViewHistory);
        resultTextView = findViewById(R.id.textViewResult);
        switch (v.getId()) {
            case R.id.button0:
                setNumber(0);
                break;
            case R.id.button1:
                setNumber(1);
                break;
            case R.id.button2:
                setNumber(2);
                break;
            case R.id.button3:
                setNumber(3);
                break;
            case R.id.button4:
                setNumber(4);
                break;
            case R.id.button5:
                setNumber(5);
                break;
            case R.id.button6:
                setNumber(6);
                break;
            case R.id.button7:
                setNumber(7);
                break;
            case R.id.button8:
                setNumber(8);
                break;
            case R.id.button9:
                setNumber(9);
                break;
            case R.id.buttonSub:
                setOp("-");
                break;
            case R.id.buttonAdd:
                setOp("+");
                break;
            case R.id.buttonDiv:
                setOp("÷");
                break;
            case R.id.buttonMul:
                setOp("×");
                break;
            case R.id.buttonClear:
                editorTextView.setText("0");
                historyTextView.setText("");
                resultTextView.setText("");
                isLastNumber = true;
                isLastOp = false;
                number = 0;
                result = 0;
                break;
            case R.id.buttonEqual:
                if (isLastOp) {
                    Toast.makeText(MainActivity.this, "Complete the Equation", Toast.LENGTH_LONG).show();
                } else if (isLastNumber && historyTextView.getText().length() == 0) {
                    resultTextView.setText(editorTextView.getText());
                } else {
                    isEqualPressed = true;
                    resultCalculation();
                }
                break;
            case R.id.buttonDot:
                isLastDot = true;
                if (isLastOp) {
                    editorTextView.append("0.");
                } else if (editorTextView.getText().charAt(editorTextView.getText().length() - 1) == '.')
                    Toast.makeText(MainActivity.this, "Not Applicable", Toast.LENGTH_LONG).show();
                else {
                    editorTextView.append(".");
                }
                break;
            case R.id.buttonBksp:
                if (editorTextView.getText().length() == 1) {
                    editorTextView.setText("0");
                    resultTextView.setText("");

                } else if (editorTextView.getText().length() > 1) {
                    int checker = Character.getNumericValue(editorTextView.getText().charAt(editorTextView.getText().length() - 2));
                    editorTextView.setText(editorTextView.getText().subSequence(0, editorTextView.getText().length() - 1));
                    resultTextView.setText("");
                    if (checker >= 0 && checker <= 9) {
                        isLastNumber = true;
                        isLastOp = false;
                    } else {
                        isLastNumber = false;
                        isLastOp = true;
                    }
                }
                break;
            default:
                Toast.makeText(MainActivity.this, "Not implemented", Toast.LENGTH_LONG).show();
        }
    }

    private void setNumber(int x) {
        if (editorTextView.getText().length() < 13) {
            if (editorTextView.getText().charAt(editorTextView.getText().length() - 1) == '0' && editorTextView.getText().length() == 1) {
                editorTextView.setText(String.valueOf(x));
                isLastNumber = true;
            } else {
                editorTextView.append(String.valueOf(x));
                isLastNumber = true;

            }
            isLastDot = false;
            isLastOp = false;
        } else {
            Toast.makeText(MainActivity.this, "Limit Reached", Toast.LENGTH_LONG).show();
        }
    }

    private void setOp(String x) {
        if (isLastDot) {
            Toast.makeText(MainActivity.this, "Not Applicable", Toast.LENGTH_LONG).show();
        } else if (isLastOp) {
            isNumSigned = editorTextView.getText().charAt(0) == '-';
            String s = editorTextView.getText().subSequence(0, editorTextView.getText().length() - 1) + x;
            editorTextView.setText(s);
            isLastOp = true;
            isLastDot = false;
            isLastNumber = false;

        } else {
            isNumSigned = editorTextView.getText().charAt(0) == '-';
            if (!isEqualPressed)
                resultCalculation();
            historyTextView.append("\n" + editorTextView.getText());
            editorTextView.setText(x);
            isLastNumber = false;
            isLastOp = true;
            isLastDot = false;
        }
    }

    private void numberExtractor() {
        if (number == 0 || isNumSigned) {
            number = Double.parseDouble(editorTextView.getText().toString());
        } else {
            number = Double.parseDouble(editorTextView.getText().subSequence(1, editorTextView.getText().length()).toString());
        }
    }

    private void resultCalculation() {
        numberExtractor();
        switch (editorTextView.getText().charAt(0)) {
            case '-':
                result += number;//no.is signed
                resultTextView.setText(Double.toString(result));
                break;
            case '+':
                result += number;
                resultTextView.setText(Double.toString(result));
                break;
            case '÷':
                result /= number;
                resultTextView.setText(Double.toString(result));
                break;
            case '×':
                result *= number;
                resultTextView.setText(Double.toString(result));
                break;
            default:
                result = number;
        }
    }
}

