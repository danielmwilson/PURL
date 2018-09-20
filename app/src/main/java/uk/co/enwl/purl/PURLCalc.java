package uk.co.enwl.purl;

/**
 * Created by GREYSTOKE\daniel on 19/09/18.
 */

import java.lang.Math; // headers MUST be above the first class
        import java.util.Arrays; // headers MUST be above the first class

// Testing class
/*
public class PURLHarness
{
    // arguments are passed using the text field below this editor
    public static void main(String[] args)
    {
        int[][] GridArray;
        String GridLine;

        boolean[] PURLT1 = new boolean[] {true,true,'x','x','x','x','x',true,true};
        boolean[] PURLT2 = new boolean[] {true,true,true,'x','x','x','x',true,true};
        boolean[] PURLT3 = new boolean[] {true,true,'x','x','x','x','x',true,true};

        PURLCalc myPURL = new PURLCalc();

        GridArray = PURLCalc.CalcGrid(PURLT1,PURLT2,PURLT3);

        for (int j=0; j<40; j++)
        {
            GridLine="";
            for (int i=0; i<40; i++)
            {
                GridLine += GridArray[i][j] + ",";
            }
            System.out.println(GridLine);
        }

    }
}
*/
// Calculation class
public class PURLCalc
{

    public PURLCalc()
    {

    }

    public static int[][] CalcGrid(boolean[] PURLT1, boolean[] PURLT2, boolean[] PURLT3)
    {
        int[][] GridArray = new int[40][40];
        double x, y, xT2, yT2, xT3, yT3;
        int[] squareStatus = new int[3];

        for (int j=0; j<40; j++)
        {
            for (int i=0; i<40; i++)
            {
                //Set coords
                x=i-20.5 +1;
                y=j-0.5+1;


                //Identify grid squares outside the circle
                if (Math.pow(x,2) + Math.pow((y-20),2) > Math.pow(20,2))
                {
                    GridArray[i][j]=0;
                }
                //Identify grid squares on the rim
                else if (Math.pow(x,2) + Math.pow((y-20),2) >= Math.pow(19,2))
                {
                    GridArray[i][j]=2;

                }
                //Internal grid squares
                else
                {
                    //T1 Analysis
                    squareStatus[0] = calcSquareStatus(x,y,PURLT1);


                    //T2 Analysis
                    //Rotate co-ordinates
                    xT2=(10 * Math.sqrt(3)) - (x / 2) - ((y * Math.sqrt(3)) / 2);
                    yT2 = 30 + ((x * Math.sqrt(3)) / 2) - (y / 2);

                    squareStatus[1] = calcSquareStatus(xT2,yT2,PURLT2);

                    //T3 Analysis
                    //Rotate co-ordinates

                    xT3= ((y * Math.sqrt(3)) / 2) - (x / 2) - (10 * Math.sqrt(3));
                    yT3= 30 - ((x * Math.sqrt(3)) / 2) - (y / 2);

                    squareStatus[2] = calcSquareStatus(xT3,yT3,PURLT3);

                    if ((squareStatus[0] == 1) || (squareStatus[1] == 1) || (squareStatus[2] == 1))
                    {
                        GridArray[i][j]=1;
                    }
                    else
                    {
                        GridArray[i][j]=-1;
                    }
                }
            }
        }
        return GridArray;
    }

    private static int calcSquareStatus(double x, double y, boolean[] PURL)
    {
        double alpha;
        int status;

        alpha=Math.abs(Math.atan(y/x));


        if (x<0)
        {
            if (((Math.PI * (3.0/24.0)) <= alpha) && (alpha <= (Math.PI * (5.0/24.0))) && (PURL[0] == true))
            {
                status=1;
            }
            else if (((Math.PI * (5.0/24.0)) <= alpha) && (alpha <= (Math.PI * (7.0/24.0))) && (PURL[1] == true))
            {
                status=1;
            }
            else if (((Math.PI * (7.0/24.0)) <= alpha) && (alpha <= (Math.PI * (9.0/24.0))) && (PURL[2] == true))
            {
                status=1;
            }
            else if (((Math.PI * (9.0/24.0)) <= alpha) && (alpha <= (Math.PI * (11.0/24.0))) && (PURL[3] == true))
            {
                status=1;
            }
            else if (((Math.PI * (11.0/24.0)) <= alpha) && (alpha <= (Math.PI * (12.0/24.0))) && (PURL[4] == true))
            {
                status=1;
            }
            else
            {
                status=0;
            }
        }
        else
        {
            if (((Math.PI * (11.0/24.0)) <= alpha) && (alpha <= (Math.PI * (12.0/24.0))) && (PURL[4] == true))
            {
                status=1;
            }
            else if (((Math.PI * (9.0/24.0)) <= alpha) && (alpha <= (Math.PI * (11.0/24.0))) && (PURL[5] == true))
            {
                status=1;
            }
            else if (((Math.PI * (7.0/24.0)) <= alpha) && (alpha <= (Math.PI * (9.0/24.0))) && (PURL[6] == true))
            {
                status=1;
            }
            else if (((Math.PI * (5.0/24.0)) <= alpha) && (alpha <= (Math.PI * (7.0/24.0))) && (PURL[7] == true))
            {
                status=1;
            }
            else if (((Math.PI * (3/24)) <= alpha) && (alpha <= (Math.PI * (5.0/24.0))) && (PURL[8] == true))
            {
                status=1;
            }
            else
            {
                status=0;
            }
        }

        return status;

    }

    public static double calcResidualStrength(int [][] GridArray)
    {
        int i,j;
        double x,y;
        double SigXi, SigYi, SigXi2, SigYi2, SigXiYiS, NumGoodSquare;
        double SigXiS, SigYiS, SigXi2S, SigYi2S, SigXiYi, NumGoodSquareS;
        double IdecayX, IdecayY, IdecayXY, Isound;
        double XBAR, YBAR, SGTHXAXIS, SGTHYAXIS;
        double[] Theta= new double[180];
        double Theta1, IdecayX1, IdecayY1, Xdash, Ydash;
        double[] NEWSTHX = new double[180];
        double[] NEWSTHY = new double[180];
        double  MINSGTHX,  MINSGTHY, ANGMINSGTHX, ANGMINSGTHY;
        double ANGMINSGTHSECTION, MINSGTHSECTION;

        //Initialise
        SigXiS =0;
        SigYiS =0;
        SigXi2S =0;
        SigYi2S =0;
        SigXiYiS =0;
        NumGoodSquareS=0;

        SigXi =0;
        SigYi =0;
        SigXi2 =0;
        SigYi2 =0;
        SigXiYi =0;
        NumGoodSquare=0;

        ANGMINSGTHX=0;
        ANGMINSGTHY=0;

        //Calculate reference parameters for a sound pole
        for (j=0; j<40; j++)
        {
            for (i=0; i<40; i++)
            {
                //Set coords
                x=i-20.5 +1;
                y=j-0.5+1;


                //Exclude grid squares outside the circle
                if (Math.pow(x,2) + Math.pow((y-20),2) <= Math.pow(20,2))
                {
                    SigXiS += x;
                    SigYiS += y;
                    SigXi2S += Math.pow(x, 2);
                    SigYi2S += Math.pow(y, 2);
                    SigXiYiS += (x * y);
                    NumGoodSquareS++;
                }
            }
        }

        //Calc parameters for pole
        for (j=0; j<40; j++)
        {
            for (i=0; i<40; i++)
            {
                //Set coords
                x=i-20.5 +1;
                y=j-0.5+1;

                //Exclude bad grid squares
                if (GridArray[i][j]>0)
                {
                    SigXi += x;
                    SigYi += y;
                    SigXi2 += Math.pow(x, 2);
                    SigYi2 += Math.pow(y, 2);
                    SigXiYi += SigXiYiS + (x * y);
                    NumGoodSquare++;
                }
            }
        }

        //(1) Calculate I values
        IdecayX = (NumGoodSquare / 12) + SigXi2 - (Math.pow(SigXi,2) / NumGoodSquare);
        IdecayY = (NumGoodSquare / 12) + SigYi2 - (Math.pow(SigYi,2) / NumGoodSquare);

        Isound = (NumGoodSquareS / 12) + SigXi2S - (Math.pow(SigXiS,2) / NumGoodSquareS);

        IdecayXY = SigXiYi - ((SigXi * SigYi) / NumGoodSquare);

        //(2) Calculate XBAR, YBAR
        XBAR = SigXi / NumGoodSquare;
        YBAR = SigYi / NumGoodSquare;


        //(3) Find SGTHYAXIS, SGTHXAXIS
        SGTHYAXIS = (IdecayX / Isound) * (20 / (20 + Math.abs(XBAR))) * 100;
        SGTHXAXIS = (IdecayY / Isound) * (20 / (20 + Math.abs(YBAR - 20))) * 100;


        //(4) Define Xdash, Ydash, IdecayX1, IdecayY1
        //Go through 90 Degree's at 1/2 Degree Intervals

        for (int K = 1; K<=180; K++)
        {

            Theta[K-1] = K / 2;
            Theta1 = Theta[K-1] * (Math.PI / 180);

            IdecayX1 = (IdecayX * (Math.pow(Math.cos(Theta1),2))) + (IdecayY * (Math.pow(Math.sin(Theta1),2))) - (IdecayXY * Math.sin(2 * Theta1));
            IdecayY1 = (IdecayX * (Math.pow(Math.sin(Theta1),2))) + (IdecayY * (Math.pow(Math.cos(Theta1),2))) + (IdecayXY * Math.sin(2 * Theta1));

            Xdash = Math.abs((XBAR * Math.cos(Theta1)) + ((YBAR - 20) * Math.sin(Theta1)));
            Ydash = Math.abs(((YBAR - 20) * Math.cos(Theta1)) - ((XBAR) * Math.sin(Theta1)));

            NEWSTHX[K-1] = (IdecayX1 / Isound) * (20 / (20 + Xdash)) * 100;
            NEWSTHY[K-1] = (IdecayY1 / Isound) * (20 / (20 + Ydash)) * 100;

        }

        //(5) Find MINSGTHX, MINSGTHY, ANGMINSGTHX, ANGMINSGTHY
        MINSGTHX = 100;
        MINSGTHY = 100;

        for (i = 1; i<=180; i++)
        {
            if (NEWSTHX[i-1] <= MINSGTHX)
            {
                MINSGTHX = NEWSTHX[i-1];
                ANGMINSGTHX = Theta[i-1] + 90;
            }
        }

        for (j = 1; j<=180; j++)
        {
            if (NEWSTHY[j-1] <= MINSGTHY)
            {
                MINSGTHY = NEWSTHY[j-1];
                ANGMINSGTHY = Theta[j-1] + 90;
            }
        }



        //(7) Find MINSGTHSECTION, ANGMINSGTHSECTION
        if (MINSGTHX < MINSGTHY)
        {
            MINSGTHSECTION = MINSGTHX;
            ANGMINSGTHSECTION = ANGMINSGTHX;
        }
        else
        {
            MINSGTHSECTION = MINSGTHY;
            ANGMINSGTHSECTION = ANGMINSGTHY;
        }

        return MINSGTHSECTION;
    }


}