

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		URLProcessor u = new URLProcessor();
		ArrayList<Keyword> keywordList = new ArrayList<>();
		
		

		System.out.println("Welcome to car search tool. You can enter \"quit\" to quit the app anytime.");
		boolean flag = true;
		while (flag) {
			if (flag == false) {
				break;
			}
			boolean loop = true;
			
			while (flag && loop) {
				System.out.println("Please enter the topic you want to search.");

				String search = sc.nextLine();
				if (search.equals("quit")) {
					flag = false;
					break;
				}
				if (search.equals("return")) {
					break;
				}

				while (flag && loop) {
					System.out.println("");
					System.out.println(
							"Please enter AT MOST 3 wanted keywords to enhance the result (Put the most important keyword in first. Use comma(,) to seperate ex:engine,cost,$$).");

					System.out.println("Or enter \"return\" to return to previous step.");

					String wantedK = sc.nextLine();
					if (wantedK.equals("quit")) {
						flag = false;
						break;
					}
					if (wantedK.equals("return")) {
						break;
					}
					if (wantedK.strip().equals("")) {
						System.out.println("Please enter at least 1 keyword.");
						continue;
					}
					
					wantedK += ",";
					String w1 = "";
					String w2 = "";
					String w3 = "";

					try {
						w1 = wantedK.substring(0, wantedK.indexOf(","));
						wantedK = wantedK.substring(wantedK.indexOf(",") + 1);
						w2 = wantedK.substring(0, wantedK.indexOf(","));
						wantedK = wantedK.substring(wantedK.indexOf(",") + 1);
						w3 = wantedK.substring(0, wantedK.length()-1);
					} catch (StringIndexOutOfBoundsException e) {

					}

					System.out.println("");

					if (!w1.equals("") && !w2.equals("") && !w3.equals("")) {
						System.out.printf("Your wanted keywords are:\n 1.%s;\n 2.%s;\n 3.%s;\n ", w1, w2, w3);
					} else if (!w1.equals("") && !w2.equals("") && w3.equals("")) {
						System.out.printf("Your wanted keywords are:\n 1.%s;\n 2.%s;\n", w1, w2);
					} else if (!w1.equals("") && w2.equals("") && w3.equals("")) {
						System.out.printf("Your wanted keywords are:\n 1.%s;\n", w1);
					}

					while (flag && loop) {
						System.out.println("");
						System.out.println(
								"Please enter AT MOST 3 UNwanted keywords to enhance the result (Put the most important keyword in first. Use comma(,) to seperate).");
						System.out.println("Enter \"return\" to return to previous step.");

						String unwantedK = sc.nextLine();
						if (unwantedK.equals("quit")) {
							flag = false;
							break;
						}
						if (unwantedK.equals("return")) {
							break;
						}
						if (unwantedK.strip().equals("")) {
							System.out.println("Please enter at least 1 keyword.");
							continue;
						}
						
						unwantedK += ",";
						
						String uw1 = "";
						String uw2 = "";
						String uw3 = "";

						try {
							uw1 = unwantedK.substring(0, unwantedK.indexOf(","));
							unwantedK = unwantedK.substring(unwantedK.indexOf(",") + 1);
							uw2 = unwantedK.substring(0, unwantedK.indexOf(","));
							unwantedK = unwantedK.substring(unwantedK.indexOf(",") + 1);
							uw3 = unwantedK.substring(0, unwantedK.length()-1);
						} catch (StringIndexOutOfBoundsException e) {

						}

						System.out.println("");

						if (!uw1.equals("") && !uw2.equals("") && !uw3.equals("")) {
							System.out.printf("Your unwanted keywords are:\n 1.%s;\n 2.%s;\n 3.%s;\n ", uw1, uw2, uw3);
						} else if (!uw1.equals("") && !uw2.equals("") && uw3.equals("")) {
							System.out.printf("Your unwanted keywords are:\n 1.%s;\n 2.%s;\n", uw1, uw2);
						} else if (!uw1.equals("") && uw2.equals("") && uw3.equals("")) {
							System.out.printf("Your unwanted keywords are:\n 1.%s;\n", uw1);
						}
						while (flag && loop) {

							System.out.println(
									"App will start searching, please check again if both group of keywords are correct.");
							System.out.println(
									"Enter anything to proceed, or enter \"return\" to return to previous step.");

							String proceed = sc.nextLine();
							if (proceed.equals("quit")) {
								flag = false;
								break;
							} else if (proceed.equals("return")) {
								break;
							}

							else {
								Web google = new Web(search);
								try {
									u.query(google);
								} catch (Exception e) {

								}

								if (!w1.equals("")) {
									keywordList.add(new Keyword(w1, 5));
								}
								if (!w2.equals("")) {
									keywordList.add(new Keyword(w2, 3));
								}
								if (!w3.equals("")) {
									keywordList.add(new Keyword(w3, 1));
								}
								if (!uw1.equals("")) {
									keywordList.add(new Keyword(uw1, -10));
								}
								if (!uw2.equals("")) {
									keywordList.add(new Keyword(uw2, -6));
								}
								if (!uw3.equals("")) {
									keywordList.add(new Keyword(uw3, -2));
								}

								for (Web w : google.getSubWebsList()) {

									try {
										WebNode node = new WebNode(w);
										WebTree tree = new WebTree(w);

										u.getChildUrls(w);
										for (Web subW : w.getSubWebsList()) {
											node.addChild(new WebNode(subW));
										}

										tree.setPostOrderScore(keywordList);

										System.out.println(tree.eularPrintTree());

										// w.printWebsList();
									} catch (IOException e) {

									}

								}

								System.out
										.println("Enter anything to exit, or enter \"return\" to return to home page.");

								String again = sc.nextLine().strip();
								if (again.equals("return")) {
									System.out.println(
											"Welcome to car search tool. You can enter \"quit\" to quit the app anytime.");
									loop = false;
									break;
								} else {
									flag = false;
									break;
								}
							}
						}
					}
				}
			}
		}

		System.out.println("Thank you for using.");
		sc.close();
	}

}
