
/*****************************************************************************************
 * @file  MovieDB.java
 *
 * @author   John Miller
 */

import static java.lang.System.out;

/*****************************************************************************************
 * The MovieDB class makes a Movie Database.  It serves as a template for making other
 * databases.  See "Database Systems: The Complete Book", second edition, page 26 for more
 * information on the Movie Database schema.
 */
class MovieDB
{
    /*************************************************************************************
     * Main method for creating, populating and querying a Movie Database.
     * @param args  the command-line arguments
     */
    public static void main (String [] args)
    {
        out.println ();

        var movie = new Table ("movie", "title year length genre studioName producerNo",
                                        "String Integer Integer String String Integer", "title year");

        var cinema = new Table ("cinema", "title year length genre studioName producerNo",
                                          "String Integer Integer String String Integer", "title year");

        var movieStar = new Table ("movieStar", "name address gender birthdate",
                                                "String String Character String", "name");

        var starsIn = new Table ("starsIn", "movieTitle movieYear starName",
                                            "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table ("movieExec", "certNo name address fee",
                                                "Integer String String Float", "certNo");

        var studio = new Table ("studio", "name address presNo",
                                          "String String Integer", "name");

        var film0 = new Comparable [] { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
        var film1 = new Comparable [] { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
        var film2 = new Comparable [] { "Rocky", 1985, 200, "action", "Universal", 12125 };
        var film3 = new Comparable [] { "Rambo", 1978, 100, "action", "Universal", 32355 };

        out.println ();
        movie.insert (film0);
        movie.insert (film1);
        movie.insert (film2);
        movie.insert (film3);
        movie.print ();

        var film4 = new Comparable [] { "Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890 };
        out.println ();
        cinema.insert (film2);
        cinema.insert (film3);
        cinema.insert (film4);
        cinema.print ();

        var star0 = new Comparable [] { "Carrie_Fisher", "Hollywood", 'F', "9/9/99" };
        var star1 = new Comparable [] { "Mark_Hamill", "Brentwood", 'M', "8/8/88" };
        var star2 = new Comparable [] { "Harrison_Ford", "Beverly_Hills", 'M', "7/7/77" };
        out.println ();
        movieStar.insert (star0);
        movieStar.insert (star1);
        movieStar.insert (star2);
        movieStar.print ();

        var cast0 = new Comparable [] { "Star_Wars", 1977, "Carrie_Fisher" };
        out.println ();
        starsIn.insert (cast0);
        starsIn.print ();

        var exec0 = new Comparable [] { 9999, "S_Spielberg", "Hollywood", 10000.00 };
        out.println ();
        movieExec.insert (exec0);
        movieExec.print ();

        var studio0 = new Comparable [] { "Fox", "Los_Angeles", 7777 };
        var studio1 = new Comparable [] { "Universal", "Universal_City", 8888 };
        var studio2 = new Comparable [] { "DreamWorks", "Universal_City", 9999 };
        out.println ();
        studio.insert (studio0);
        studio.insert (studio1);
        studio.insert (studio2);
        studio.print ();

        movie.save ();
        cinema.save ();
        movieStar.save ();
        starsIn.save ();
        movieExec.save ();
        studio.save ();

        movieStar.printIndex ();

        //--------------------- project: title year

        out.println ();
        var t_project = movie.project ("title year");
        t_project.print ();

        //--------------------- select: equals, &&

        out.println ();
        var t_select = movie.select (t -> t[movie.col("title")].equals ("Star_Wars") &&
                                            t[movie.col("year")].equals (1977));
        t_select.print ();

        //--------------------- select: <

        out.println ();
        var t_select2 = movie.select (t -> (Integer) t[movie.col("year")] < 1980);
        t_select2.print ();

        //--------------------- indexed select: key

        out.println ();
        var t_iselect = movieStar.select (new KeyType ("Harrison_Ford"));
        t_iselect.print ();
        
        //Select Test #1
        //Expected Output: Table that contains the movie Rambo from the Table cinema
        //Table with selected tuple should be printed to the console
        out.println("Select Test #1");
        var selectTest1 = cinema.select(new KeyType("Rambo", 1978));
        selectTest1.print();
        
        //Select Test #2
        //Expected Output: Table that contains the movie exec S_Speilberg from the Table movieExec
        //Table with selected tuple should be printed to the console
        out.println("Select Test #2");
        var selectTest2 = movieExec.select(new KeyType(9999));
        selectTest2.print();
        
        //Select Test #3
        //Expected Output: Table that contains the movie Star Wars from the Table movie
        //Table with selected tuple should be printed to the console
        out.println("Select Test #3");
        var selectTest3 = movie.select(new KeyType("Star_Wars", 1977));
        selectTest3.print();
        
        //Select Test #4
        //Expected Output: Table that contains the star Mark Hamill from the Table movieStar
        //Table with selected tuple should be printed to the console
        out.println("Select Test #4");
        var selectTest4 = movieStar.select(new KeyType("Mark_Hamill"));
        selectTest4.print();
        
        //Select Test #5
        //Expected Output: Table that contains the studio Universal from the table studio
        //Table with selected tuple should be printed to the console
        out.println("Select Test #5");
        var selectTest5 = studio.select(new KeyType("Universal"));
        selectTest5.print();

        //--------------------- union: movie UNION cinema

        out.println ("union");
        var t_union = movie.union (cinema);
        t_union.print ();

        //--------------------- minus: movie MINUS cinema

        out.println ();
        var t_minus = movie.minus (cinema);
        t_minus.print ();

        //--------------------- equi-join: movie JOIN studio ON studioName = name

        out.println ();
        var t_join = movie.join ("studioName", "name", studio);
        t_join.print ();

        //--------------------- natural join: movie JOIN studio

        var kev_test1 = movieStar.join(starsIn);
        kev_test1.print ();
        var kev_test2 = movie.join(studio);
        kev_test2.print();
        var kev_test3 = movieExec.join(movieStar);
        kev_test3.print();
        var kev_test4 = starsIn.join(movieStar);
        kev_test4.print();
        var kev_test5 = movieExec.join(studio);
        kev_test5.print();

    } // main

} // MovieDB class
