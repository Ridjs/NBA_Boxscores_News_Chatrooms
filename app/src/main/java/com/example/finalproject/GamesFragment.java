package com.example.finalproject;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView noGames;
    ListView listViewGames;
    ArrayList<Match> arrayListGames;
    CustomAdapterGames customAdapterGames;
    static String favTeam;
    private FirebaseAuth mAuth;
    public static final String[] teams = new String[]{"Atlanta Hawks","Boston Celtics","Brooklyn Nets","Charlotte Hornets","Chicago Bulls","Cleveland Cavaliers","Dallas Mavericks","Denver Nuggets","Detroit Pistons","Golden State Warriors","Houston Rockets","Indiana Pacers","LA Clippers","Los Angeles Lakers","Memphis Grizzlies","Miami Heat","Milwaukee Bucks","Minnesota Timberwolves","New Orleans Pelicans","New York Knicks","Oklahoma City Thunder","Orlando Magic","Philadelphia 76ers","Phoenix Suns","Portland Trail Blazers","Sacramento Kings","San Antonio Spurs","Toronto Raptors","Utah Jazz","Washington Wizards"};
    String jsonData = "{\n" +
            "  \"get\": \"games/\",\n" +
            "  \"parameters\": {\n" +
            "    \"date\": \"2022-03-09\"\n" +
            "  },\n" +
            "  \"errors\": [],\n" +
            "  \"results\": 6,\n" +
            "  \"response\": [\n" +
            "    {\n" +
            "      \"id\": 10540,\n" +
            "      \"league\": \"standard\",\n" +
            "      \"season\": 2021,\n" +
            "      \"date\": {\n" +
            "        \"start\": \"2022-03-09T00:00:00.000Z\",\n" +
            "        \"end\": \"2022-03-09T02:24:00.000Z\",\n" +
            "        \"duration\": \"2:13\"\n" +
            "      },\n" +
            "      \"stage\": 2,\n" +
            "      \"status\": {\n" +
            "        \"clock\": null,\n" +
            "        \"halftime\": false,\n" +
            "        \"short\": 3,\n" +
            "        \"long\": \"Finished\"\n" +
            "      },\n" +
            "      \"periods\": {\n" +
            "        \"current\": 4,\n" +
            "        \"total\": 4,\n" +
            "        \"endOfPeriod\": false\n" +
            "      },\n" +
            "      \"arena\": {\n" +
            "        \"name\": \"Spectrum Center\",\n" +
            "        \"city\": \"Charlotte\",\n" +
            "        \"state\": \"NC\",\n" +
            "        \"country\": \"USA\"\n" +
            "      },\n" +
            "      \"teams\": {\n" +
            "        \"visitors\": {\n" +
            "          \"id\": 4,\n" +
            "          \"name\": \"Brooklyn Nets\",\n" +
            "          \"nickname\": \"Nets\",\n" +
            "          \"code\": \"BKN\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Brooklyn_Nets_newlogo.svg/130px-Brooklyn_Nets_newlogo.svg.png\"\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"id\": 5,\n" +
            "          \"name\": \"Charlotte Hornets\",\n" +
            "          \"nickname\": \"Hornets\",\n" +
            "          \"code\": \"CHA\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/thumb/f/f3/Hornets_de_Charlotte_logo.svg/1200px-Hornets_de_Charlotte_logo.svg.png\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"scores\": {\n" +
            "        \"visitors\": {\n" +
            "          \"win\": 33,\n" +
            "          \"loss\": 33,\n" +
            "          \"series\": {\n" +
            "            \"win\": 1,\n" +
            "            \"loss\": 1\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"34\",\n" +
            "            \"35\",\n" +
            "            \"31\",\n" +
            "            \"32\"\n" +
            "          ],\n" +
            "          \"points\": 132\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"win\": 32,\n" +
            "          \"loss\": 34,\n" +
            "          \"series\": {\n" +
            "            \"win\": 1,\n" +
            "            \"loss\": 1\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"20\",\n" +
            "            \"23\",\n" +
            "            \"41\",\n" +
            "            \"37\"\n" +
            "          ],\n" +
            "          \"points\": 121\n" +
            "        }\n" +
            "      },\n" +
            "      \"officials\": [\n" +
            "        \"Scott Foster\",\n" +
            "        \"Lauren Holtkamp\",\n" +
            "        \"Phenizee Ransom\"\n" +
            "      ],\n" +
            "      \"timesTied\": 3,\n" +
            "      \"leadChanges\": 2,\n" +
            "      \"nugget\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10541,\n" +
            "      \"league\": \"standard\",\n" +
            "      \"season\": 2021,\n" +
            "      \"date\": {\n" +
            "        \"start\": \"2022-03-09T00:00:00.000Z\",\n" +
            "        \"end\": \"2022-03-09T02:27:00.000Z\",\n" +
            "        \"duration\": \"2:17\"\n" +
            "      },\n" +
            "      \"stage\": 2,\n" +
            "      \"status\": {\n" +
            "        \"clock\": null,\n" +
            "        \"halftime\": false,\n" +
            "        \"short\": 3,\n" +
            "        \"long\": \"Finished\"\n" +
            "      },\n" +
            "      \"periods\": {\n" +
            "        \"current\": 4,\n" +
            "        \"total\": 4,\n" +
            "        \"endOfPeriod\": false\n" +
            "      },\n" +
            "      \"arena\": {\n" +
            "        \"name\": \"Gainbridge Fieldhouse\",\n" +
            "        \"city\": \"Indianapolis\",\n" +
            "        \"state\": \"IN\",\n" +
            "        \"country\": \"USA\"\n" +
            "      },\n" +
            "      \"teams\": {\n" +
            "        \"visitors\": {\n" +
            "          \"id\": 7,\n" +
            "          \"name\": \"Cleveland Cavaliers\",\n" +
            "          \"nickname\": \"Cavaliers\",\n" +
            "          \"code\": \"CLE\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/thumb/0/06/Cavs_de_Cleveland_logo_2017.png/150px-Cavs_de_Cleveland_logo_2017.png\"\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"id\": 15,\n" +
            "          \"name\": \"Indiana Pacers\",\n" +
            "          \"nickname\": \"Pacers\",\n" +
            "          \"code\": \"IND\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/thumb/c/cf/Pacers_de_l%27Indiana_logo.svg/1180px-Pacers_de_l%27Indiana_logo.svg.png\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"scores\": {\n" +
            "        \"visitors\": {\n" +
            "          \"win\": 38,\n" +
            "          \"loss\": 27,\n" +
            "          \"series\": {\n" +
            "            \"win\": 4,\n" +
            "            \"loss\": 0\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"32\",\n" +
            "            \"31\",\n" +
            "            \"27\",\n" +
            "            \"37\"\n" +
            "          ],\n" +
            "          \"points\": 127\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"win\": 22,\n" +
            "          \"loss\": 45,\n" +
            "          \"series\": {\n" +
            "            \"win\": 0,\n" +
            "            \"loss\": 4\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"24\",\n" +
            "            \"39\",\n" +
            "            \"35\",\n" +
            "            \"26\"\n" +
            "          ],\n" +
            "          \"points\": 124\n" +
            "        }\n" +
            "      },\n" +
            "      \"officials\": [\n" +
            "        \"Zach Zarba\",\n" +
            "        \"Nick Buchert\",\n" +
            "        \"Gediminas Petraitis\"\n" +
            "      ],\n" +
            "      \"timesTied\": 14,\n" +
            "      \"leadChanges\": 11,\n" +
            "      \"nugget\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10542,\n" +
            "      \"league\": \"standard\",\n" +
            "      \"season\": 2021,\n" +
            "      \"date\": {\n" +
            "        \"start\": \"2022-03-09T00:00:00.000Z\",\n" +
            "        \"end\": \"2022-03-09T02:26:00.000Z\",\n" +
            "        \"duration\": \"2:18\"\n" +
            "      },\n" +
            "      \"stage\": 2,\n" +
            "      \"status\": {\n" +
            "        \"clock\": null,\n" +
            "        \"halftime\": false,\n" +
            "        \"short\": 3,\n" +
            "        \"long\": \"Finished\"\n" +
            "      },\n" +
            "      \"periods\": {\n" +
            "        \"current\": 4,\n" +
            "        \"total\": 4,\n" +
            "        \"endOfPeriod\": false\n" +
            "      },\n" +
            "      \"arena\": {\n" +
            "        \"name\": \"Amway Center\",\n" +
            "        \"city\": \"Orlando\",\n" +
            "        \"state\": \"FL\",\n" +
            "        \"country\": \"USA\"\n" +
            "      },\n" +
            "      \"teams\": {\n" +
            "        \"visitors\": {\n" +
            "          \"id\": 28,\n" +
            "          \"name\": \"Phoenix Suns\",\n" +
            "          \"nickname\": \"Suns\",\n" +
            "          \"code\": \"PHX\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/5/56/Phoenix_Suns_2013.png\"\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"id\": 26,\n" +
            "          \"name\": \"Orlando Magic\",\n" +
            "          \"nickname\": \"Magic\",\n" +
            "          \"code\": \"ORL\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/b/bd/Orlando_Magic_logo_2010.png\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"scores\": {\n" +
            "        \"visitors\": {\n" +
            "          \"win\": 52,\n" +
            "          \"loss\": 13,\n" +
            "          \"series\": {\n" +
            "            \"win\": 2,\n" +
            "            \"loss\": 0\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"28\",\n" +
            "            \"25\",\n" +
            "            \"26\",\n" +
            "            \"23\"\n" +
            "          ],\n" +
            "          \"points\": 102\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"win\": 16,\n" +
            "          \"loss\": 50,\n" +
            "          \"series\": {\n" +
            "            \"win\": 0,\n" +
            "            \"loss\": 2\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"22\",\n" +
            "            \"21\",\n" +
            "            \"28\",\n" +
            "            \"28\"\n" +
            "          ],\n" +
            "          \"points\": 99\n" +
            "        }\n" +
            "      },\n" +
            "      \"officials\": [\n" +
            "        \"Sean Corbin\",\n" +
            "        \"John Goble\",\n" +
            "        \"John Butler\"\n" +
            "      ],\n" +
            "      \"timesTied\": 6,\n" +
            "      \"leadChanges\": 4,\n" +
            "      \"nugget\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10543,\n" +
            "      \"league\": \"standard\",\n" +
            "      \"season\": 2021,\n" +
            "      \"date\": {\n" +
            "        \"start\": \"2022-03-09T00:30:00.000Z\",\n" +
            "        \"end\": \"2022-03-09T03:34:00.000Z\",\n" +
            "        \"duration\": \"2:17\"\n" +
            "      },\n" +
            "      \"stage\": 2,\n" +
            "      \"status\": {\n" +
            "        \"clock\": null,\n" +
            "        \"halftime\": false,\n" +
            "        \"short\": 3,\n" +
            "        \"long\": \"Finished\"\n" +
            "      },\n" +
            "      \"periods\": {\n" +
            "        \"current\": 4,\n" +
            "        \"total\": 4,\n" +
            "        \"endOfPeriod\": false\n" +
            "      },\n" +
            "      \"arena\": {\n" +
            "        \"name\": \"FedExForum\",\n" +
            "        \"city\": \"Memphis\",\n" +
            "        \"state\": \"TN\",\n" +
            "        \"country\": \"USA\"\n" +
            "      },\n" +
            "      \"teams\": {\n" +
            "        \"visitors\": {\n" +
            "          \"id\": 23,\n" +
            "          \"name\": \"New Orleans Pelicans\",\n" +
            "          \"nickname\": \"Pelicans\",\n" +
            "          \"code\": \"NOP\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/thumb/2/21/New_Orleans_Pelicans.png/200px-New_Orleans_Pelicans.png\"\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"id\": 19,\n" +
            "          \"name\": \"Memphis Grizzlies\",\n" +
            "          \"nickname\": \"Grizzlies\",\n" +
            "          \"code\": \"MEM\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/en/thumb/f/f1/Memphis_Grizzlies.svg/1200px-Memphis_Grizzlies.svg.png\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"scores\": {\n" +
            "        \"visitors\": {\n" +
            "          \"win\": 27,\n" +
            "          \"loss\": 38,\n" +
            "          \"series\": {\n" +
            "            \"win\": 1,\n" +
            "            \"loss\": 2\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"34\",\n" +
            "            \"24\",\n" +
            "            \"26\",\n" +
            "            \"27\"\n" +
            "          ],\n" +
            "          \"points\": 111\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"win\": 45,\n" +
            "          \"loss\": 22,\n" +
            "          \"series\": {\n" +
            "            \"win\": 2,\n" +
            "            \"loss\": 1\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"44\",\n" +
            "            \"33\",\n" +
            "            \"35\",\n" +
            "            \"20\"\n" +
            "          ],\n" +
            "          \"points\": 132\n" +
            "        }\n" +
            "      },\n" +
            "      \"officials\": [\n" +
            "        \"Kane Fitzgerald\",\n" +
            "        \"Natalie Sago\",\n" +
            "        \"Jenna Schroeder\"\n" +
            "      ],\n" +
            "      \"timesTied\": 0,\n" +
            "      \"leadChanges\": 0,\n" +
            "      \"nugget\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10544,\n" +
            "      \"league\": \"standard\",\n" +
            "      \"season\": 2021,\n" +
            "      \"date\": {\n" +
            "        \"start\": \"2022-03-09T01:00:00.000Z\",\n" +
            "        \"end\": \"2022-03-09T03:19:00.000Z\",\n" +
            "        \"duration\": \"2:09\"\n" +
            "      },\n" +
            "      \"stage\": 2,\n" +
            "      \"status\": {\n" +
            "        \"clock\": null,\n" +
            "        \"halftime\": false,\n" +
            "        \"short\": 3,\n" +
            "        \"long\": \"Finished\"\n" +
            "      },\n" +
            "      \"periods\": {\n" +
            "        \"current\": 4,\n" +
            "        \"total\": 4,\n" +
            "        \"endOfPeriod\": false\n" +
            "      },\n" +
            "      \"arena\": {\n" +
            "        \"name\": \"Paycom Center\",\n" +
            "        \"city\": \"Oklahoma City\",\n" +
            "        \"state\": \"OK\",\n" +
            "        \"country\": \"USA\"\n" +
            "      },\n" +
            "      \"teams\": {\n" +
            "        \"visitors\": {\n" +
            "          \"id\": 21,\n" +
            "          \"name\": \"Milwaukee Bucks\",\n" +
            "          \"nickname\": \"Bucks\",\n" +
            "          \"code\": \"MIL\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/3/34/Bucks2015.png\"\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"id\": 25,\n" +
            "          \"name\": \"Oklahoma City Thunder\",\n" +
            "          \"nickname\": \"Thunder\",\n" +
            "          \"code\": \"OKC\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/thumb/4/4f/Thunder_d%27Oklahoma_City_logo.svg/1200px-Thunder_d%27Oklahoma_City_logo.svg.png\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"scores\": {\n" +
            "        \"visitors\": {\n" +
            "          \"win\": 41,\n" +
            "          \"loss\": 25,\n" +
            "          \"series\": {\n" +
            "            \"win\": 2,\n" +
            "            \"loss\": 0\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"39\",\n" +
            "            \"37\",\n" +
            "            \"29\",\n" +
            "            \"37\"\n" +
            "          ],\n" +
            "          \"points\": 142\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"win\": 20,\n" +
            "          \"loss\": 45,\n" +
            "          \"series\": {\n" +
            "            \"win\": 0,\n" +
            "            \"loss\": 2\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"34\",\n" +
            "            \"31\",\n" +
            "            \"28\",\n" +
            "            \"22\"\n" +
            "          ],\n" +
            "          \"points\": 115\n" +
            "        }\n" +
            "      },\n" +
            "      \"officials\": [\n" +
            "        \"JB DeRosa\",\n" +
            "        \"Leon Wood\",\n" +
            "        \"Tre Maddox\"\n" +
            "      ],\n" +
            "      \"timesTied\": 6,\n" +
            "      \"leadChanges\": 1,\n" +
            "      \"nugget\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10545,\n" +
            "      \"league\": \"standard\",\n" +
            "      \"season\": 2021,\n" +
            "      \"date\": {\n" +
            "        \"start\": \"2022-03-09T03:00:00.000Z\",\n" +
            "        \"end\": \"2022-03-09T05:31:00.000Z\",\n" +
            "        \"duration\": \"2:15\"\n" +
            "      },\n" +
            "      \"stage\": 2,\n" +
            "      \"status\": {\n" +
            "        \"clock\": null,\n" +
            "        \"halftime\": false,\n" +
            "        \"short\": 3,\n" +
            "        \"long\": \"Finished\"\n" +
            "      },\n" +
            "      \"periods\": {\n" +
            "        \"current\": 4,\n" +
            "        \"total\": 4,\n" +
            "        \"endOfPeriod\": false\n" +
            "      },\n" +
            "      \"arena\": {\n" +
            "        \"name\": \"Chase Center\",\n" +
            "        \"city\": \"San Francisco\",\n" +
            "        \"state\": \"CA\",\n" +
            "        \"country\": \"USA\"\n" +
            "      },\n" +
            "      \"teams\": {\n" +
            "        \"visitors\": {\n" +
            "          \"id\": 16,\n" +
            "          \"name\": \"LA Clippers\",\n" +
            "          \"nickname\": \"Clippers\",\n" +
            "          \"code\": \"LAC\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/d/d6/Los_Angeles_Clippers_logo_2010.png\"\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"id\": 11,\n" +
            "          \"name\": \"Golden State Warriors\",\n" +
            "          \"nickname\": \"Warriors\",\n" +
            "          \"code\": \"GSW\",\n" +
            "          \"logo\": \"https://upload.wikimedia.org/wikipedia/fr/thumb/d/de/Warriors_de_Golden_State_logo.svg/1200px-Warriors_de_Golden_State_logo.svg.png\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"scores\": {\n" +
            "        \"visitors\": {\n" +
            "          \"win\": 34,\n" +
            "          \"loss\": 33,\n" +
            "          \"series\": {\n" +
            "            \"win\": 1,\n" +
            "            \"loss\": 3\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"21\",\n" +
            "            \"15\",\n" +
            "            \"25\",\n" +
            "            \"36\"\n" +
            "          ],\n" +
            "          \"points\": 97\n" +
            "        },\n" +
            "        \"home\": {\n" +
            "          \"win\": 44,\n" +
            "          \"loss\": 21,\n" +
            "          \"series\": {\n" +
            "            \"win\": 3,\n" +
            "            \"loss\": 1\n" +
            "          },\n" +
            "          \"linescore\": [\n" +
            "            \"21\",\n" +
            "            \"33\",\n" +
            "            \"32\",\n" +
            "            \"26\"\n" +
            "          ],\n" +
            "          \"points\": 112\n" +
            "        }\n" +
            "      },\n" +
            "      \"officials\": [\n" +
            "        \"Brent Barnaky\",\n" +
            "        \"J.T. Orr\",\n" +
            "        \"James Williams\"\n" +
            "      ],\n" +
            "      \"timesTied\": 5,\n" +
            "      \"leadChanges\": 3,\n" +
            "      \"nugget\": null\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public GamesFragment() {
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamesFragment newInstance(String param1, String param2) {
        GamesFragment fragment = new GamesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);
        listViewGames = (ListView)rootView.findViewById(R.id.id_listView_games);
        noGames = (TextView)rootView.findViewById(R.id.textView8);
        arrayListGames = new ArrayList<>();
        customAdapterGames = new CustomAdapterGames(getContext(),R.layout.adapter_layout_games,arrayListGames);
        listViewGames.setAdapter(customAdapterGames);
        checkFile();
        return rootView;
    }
    public void checkFile(){
        File file = new File(getContext().getFilesDir(),mAuth.getUid()+".txt");
        if(!file.exists()){
            Dialog customDialog = new Dialog(getContext());
            customDialog.setContentView(R.layout.custom_layout);
            customDialog.setCancelable(false);
            EditText team = customDialog.findViewById(R.id.editTextText);
            Button back = customDialog.findViewById(R.id.button3);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Arrays.asList(teams).contains(team.getText().toString().trim())){
                        try{
                            file.createNewFile();
                            FileWriter fileWriter = new FileWriter(file);
                            fileWriter.write(team.getText().toString().trim());
                            fileWriter.close();
                            favTeam = team.getText().toString().trim();
                            //TextView profileTeam = (TextView)MainApp.headerNav.findViewById(R.id.profile_team);
                            //profileTeam.setText(favTeam);
                            new GetMatchesInfo().execute();
                            customDialog.dismiss();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        team.setError("NBA TEAM REQUIRED");
                    }
                }
            });
            customDialog.show();
        }
        else{
            try{
                FileReader fileReader = new FileReader(file);
                StringBuilder str = new StringBuilder();
                int j;
                while((j=fileReader.read())!=-1){
                    str.append((char)j);
                }
                favTeam = String.valueOf(str);
                fileReader.close();
                new GetMatchesInfo().execute();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private class GetMatchesInfo extends AsyncTask<Void,Void,Void> {
        String responseString;
        @Override
        protected Void doInBackground(Void... voids) {
            responseString = jsonData;
            /*try {
                responseString = new String();
                Date tomorrowDate = new Date(new Date().getTime()+(24*60*60*1000));
                String tomorrowDateString = new SimpleDateFormat("yyyy-MM-dd").format(tomorrowDate);
                URL apiCall = new URL("https://v2.nba.api-sports.io/games?date="+tomorrowDateString);
                HttpURLConnection apiCallConnection = (HttpURLConnection)apiCall.openConnection();
                apiCallConnection.setRequestMethod("GET");
                apiCallConnection.setRequestProperty("x-rapidapi-host", "v2.nba.api-sports.io");
                apiCallConnection.setRequestProperty("x-rapidapi-key", "secured");
                InputStream inputStream = apiCallConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String reader;
                while ((reader = bufferedReader.readLine()) != null) {
                    responseString += reader;
                }
                bufferedReader.close();
                inputStream.close();
                apiCallConnection.disconnect();
            }catch(IOException e){
            }*/
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try{
                JSONObject json = new JSONObject(responseString);
                JSONArray gamesArray = json.getJSONArray("response");
                for (int i = 0; i < gamesArray.length(); i++) {
                    JSONObject game = gamesArray.getJSONObject(i);
                    JSONObject visitorsTeam = game.getJSONObject("teams").getJSONObject("visitors");
                    String visitorsTeamName = visitorsTeam.getString("name");
                    JSONObject homeTeam = game.getJSONObject("teams").getJSONObject("home");
                    String homeTeamName = homeTeam.getString("name");
                    String status = game.getJSONObject("status").getString("long");
                    int scoreHome = game.getJSONObject("scores").getJSONObject("home").optInt("points",0);
                    int scoreAway = game.getJSONObject("scores").getJSONObject("visitors").optInt("points",0);
                    if(homeTeamName.equals(favTeam)||visitorsTeamName.equals(favTeam)){
                        arrayListGames.add(0,new Match(homeTeamName,visitorsTeamName,status,scoreHome,scoreAway));
                    }
                    else{
                        arrayListGames.add(new Match(homeTeamName,visitorsTeamName,status,scoreHome,scoreAway));
                    }
                    customAdapterGames.notifyDataSetChanged();
                }
                noGames.setVisibility(View.INVISIBLE);
            }catch(JSONException e){
                noGames.setVisibility(View.VISIBLE);
                cancel(true);
            }
            if(arrayListGames.size()==0){
                noGames.setVisibility(View.VISIBLE);
            }
        }
    }
    public static class Match{
        private final String homeTeam;
        private final String awayTeam;
        private final String status;
        private final int scoreHome;
        private final int scoreAway;
        public Match(String homeTeam, String awayTeam,String status,int scoreHome,int scoreAway){
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.status = status;
            this.scoreHome = scoreHome;
            this.scoreAway = scoreAway;
        }
        public String getHomeTeam(){
            return this.homeTeam;
        }
        public String getAwayTeam(){
            return this.awayTeam;
        }
        public String getStatus() {
            return this.status;
        }
        public int getScoreHome() {
            return this.scoreHome;
        }
        public int getScoreAway() {
            return this.scoreAway;
        }
    }
}