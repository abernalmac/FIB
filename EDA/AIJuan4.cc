#include "Player.hh"

//Only treasure search was needed in order to beat the bot. That's what this does. 
/**
 * Write the name of your player and save this file
 * with the same name and .cc extension.
 */
#define PLAYER_NAME Juan4
#define INT_MAX 2147483647

struct PLAYER_NAME : public Player {

  /**
   * Factory: returns a new instance of this class.
   * Do not modify this function.
   */
  static Player* factory () {
    return new PLAYER_NAME;
  }

  /**
   * Types and attributes for your player can be defined here.
   */
  typedef pair<int,Pos> pedge;
  vector<Dir> dirs = {Bottom,BR,Right,RT,Top,TL,Left,LB};      
  
  Dir runforyourlife(Dir &d) {
      if (d == Bottom) return Top;
      if (d == BR) return TL;
      if (d == Right) return Left;
      if (d == RT) return LB;
      if (d == Top) return Bottom;
      if (d == TL) return BR;
      if (d == Left) return Right;
      if (d == LB) return RT;
      return None;
  }
  
  void bfs(vector<vector<int>> &dist, Pos p) {
    queue<Pos> q;
    q.push(p);
    while (not q.empty()) {
        Pos p1 = q.front();
        q.pop();
        for (auto d : dirs) {
            Pos p2 = p1 + d;
            if (pos_ok(p2) and (dist[p2.i][p2.j] == INT_MAX-1 or dist[p2.i][p2.j] > dist[p1.i][p1.j] + 1) and dist[p2.i][p2.j] != INT_MAX) {
                q.push(p2);
                dist[p2.i][p2.j] = dist[p1.i][p1.j] + 1;
            }
        }
    }
  }
  
  void dwarf_bfs(vector<vector<int>> &distogold) {
      for (unsigned int i = 0; i < 59; ++i) {
          for (unsigned int j = 0; j < 59; ++j) {
              Pos p(i,j); 
              Cell c = cell(p);
              if (c.type == Granite) {
                  distogold[p.i][p.j] = INT_MAX;
              }
          }
      }
      for (unsigned int i = 0; i < 59; ++i) {
          for (unsigned int j = 0; j < 59; ++j) {
              Pos p(i,j); 
              Cell c = cell(p);
              if (c.treasure) {
                  distogold[p.i][p.j] = 0;
                  bfs(distogold,p);
              }
          }
      }
  }
  
  void wiz_bfs(vector<vector<int>> &distodwarf) {
      vector<int> v = dwarves(me());
      for (unsigned int i = 0; i < v.size(); ++i) {
          Pos p = unit(v[i]).pos;
            distodwarf[p.i][p.j] = 0;
      }
      for (unsigned int i = 0; i < v.size(); ++i) {
          Pos p = unit(v[i]).pos;
          bfs(distodwarf,p);
      }   
  }
  
  void balrog_bfs(vector<vector<int>> &distobalrog) {
      Pos p = unit(balrog_id()).pos;
      queue<Pos> q;
      q.push(p);
      distobalrog[p.i][p.j] = 0;
      while (not q.empty()) {
          Pos p1 = q.front();
          q.pop();
          for (auto d : dirs) {
              Pos p2 = p1 + d;
              if (pos_ok(p2) and (distobalrog[p2.i][p2.j] == INT_MAX or distobalrog[p2.i][p2.j] > distobalrog[p1.i][p1.j] + 1)) {
                  q.push(p2);
                  distobalrog[p2.i][p2.j] = distobalrog[p1.i][p1.j] + 1;
              }
          }
      }
  }
  
  void move_dwarf(vector<vector<int>> &distogold, vector<vector<int>> &distobalrog) {
      vector<int> v = dwarves(me());
      for (unsigned int i = 0; i < v.size(); ++i) {
          Pos p = unit(v[i]).pos;
          int min = INT_MAX;
          Dir f = None;
          bool gold = false;
          bool enemy = false;
          for (auto d : dirs) {
              if (pos_ok(p+d)) {
                  Pos aux = p+d;
                  Cell test = cell(aux);
                  if (test.treasure) {
                      f = d;
                      gold = true;
                      command(v[i],f);
                  }
              }
          }
          if (distobalrog[p.i][p.j] < 3 and not gold) {
              for (auto d : dirs) {
                  if (pos_ok(p+d)) {
                      Pos aux = p+d;
                      if (distobalrog[aux.i][aux.j] < min) {
                          min = distobalrog[aux.i][aux.j];
                          f = d;
                      }
                  }
              }
              Dir ok = runforyourlife(f);
              Pos p1 = p + ok;
              Cell test = cell(p1);
              if (test.type == Granite or test.type == Rock or test.type == Abyss) {
                  for (auto d : dirs) {
                      if (d != f) {
                          if (pos_ok(p+d)) {
                              Pos aux = p+d;
                              if (distobalrog[aux.i][aux.j] < min) {
                                  min = distobalrog[aux.i][aux.j];
                                  f = d;
                              }
                          }
                      }
                  }
                  f = runforyourlife(f);
                  p1 = p + f;
              }
              if (pos_ok(p1)) command(v[i],f);
          }
          else if (not gold) {
              for (unsigned int x = 0; x < dirs.size() and not enemy; ++x) {
                    if (pos_ok(p+dirs[x])) {
                        Pos aux = p+dirs[x];
                        int id2 = cell(aux).id;
                        if (id2 != -1 and unit(id2).player != me()) {
                            enemy = true;
                            f = dirs[x];
                            command(v[i],f);
                        }
                    }
              }
          }
          if (not gold and not enemy) {
              for (auto d : dirs) {
                  if (pos_ok(p+d)) { 
                    Pos aux = p+d;
                    Cell c = cell(aux);
                    if (distogold[aux.i][aux.j] < min and c.type != Granite and c.type != Abyss) {
                        min = distogold[aux.i][aux.j];
                        f = d;
                    }
                  }
              }
              Pos p1 = p + f;
              Cell test = cell(p1);
              Dir newdir = f;
              if (test.type == Granite or test.type == Abyss) {
                  for (auto d : dirs) {
                      if (d != f) {
                          if (pos_ok(p+d)) {
                              Pos aux = p+d;
                              Cell c = cell(aux);
                              if (distogold[aux.i][aux.j] < min and c.type != Granite and c.type != Abyss) {
                                  min = distogold[aux.i][aux.j];
                                  newdir = d;
                              }
                          }
                      }
                  }
              }
              command(v[i],newdir);
          }
      }      
  }

  void move_wizard(vector<vector<int>> &distodwarf, vector<vector<int>> &distobalrog) {
      vector<int> v = wizards(me());
      for (unsigned int i = 0; i < v.size(); ++i) {
          Pos p = unit(v[i]).pos;
          Dir f = None;
          int min = INT_MAX;
          for (auto d : dirs) {
              if (pos_ok(p+d)) {
                  Pos aux = p+d;
                  if (distodwarf[aux.i][aux.j] < min) {
                    min = distodwarf[aux.i][aux.j];
                    f = d;
                  }
              }
          }
          Cell c = cell(p+f);
          if (pos_ok(p+f) and c.type != Abyss and c.type != Granite and c.type != Rock) 
            command(v[i],f);  
          else { 
              Dir newdir = f;
              for (auto d : dirs) {
                if (d != f and pos_ok(p+d)) {
                    Pos aux = p+d;
                    if (distodwarf[aux.i][aux.j] < min) {
                        min = distodwarf[aux.i][aux.j];
                        newdir = d;
                    }
                }
              }
              if (pos_ok(p+f) and c.type != Abyss and c.type != Granite and c.type != Rock) 
            command(v[i],newdir);
          }
      }
  }
  
  /**
   * Play method, invoked once per each round.
   */
  virtual void play () {
      vector<vector<int>> distogold(60,vector<int> (60,INT_MAX-1));
      vector<vector<int>> distodwarf(60,vector<int> (60,INT_MAX));
      vector<vector<int>> distobalrog(60,vector<int> (60,INT_MAX));
      dwarf_bfs(distogold);
      wiz_bfs(distodwarf);
      balrog_bfs(distobalrog);
      /*if (round()== 199) {
        for (int i = 0; i < 60; ++i) {
            for (int j = 0; j < 60; ++j) 
                cerr << distogold[i][j] << ' ';
            cerr << endl;
        }
      }
      if (round()== 1) {
        for (int i = 0; i < 60; ++i) {
            for (int j = 0; j < 60; ++j) 
                cerr << distodwarf[i][j] << ' ';
            cerr << endl;
        }
      }
      if (round()== 199) {
        for (int i = 0; i < 60; ++i) {
            for (int j = 0; j < 60; ++j) 
                cerr << distobalrog[i][j] << ' ';
            cerr << endl;
        }
      }*/
      move_dwarf(distogold,distobalrog);
      move_wizard(distodwarf,distobalrog);
  }

};


/**
 * Do not modify the following line.
 */
RegisterPlayer(PLAYER_NAME);
