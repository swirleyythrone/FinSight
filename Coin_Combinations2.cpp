#include <bits/stdc++.h>
typedef long long ll;
int M=1e9+7;
using namespace std;


class Solution{
  public:
    
    int numberOfWays(int n, int x, vector<int> &coins){
      vector<ll>next(x+1,0),curr(x+1,0);
      next[0]=1;
      for(int ind = n-1 ; ind >= 0 ; ind--){
        for(int sum = 0 ; sum <= x ; sum++){
          int notTake=next[sum];
          int take=0;
          if(coins[ind]<=sum)take=curr[sum-coins[ind]];
          curr[sum]=(take+notTake)%M;
        }
        next=curr;
      }
      return next[x]%M;
    }
  
};

void solve()
{
  int n,x;
  cin>>n>>x;
  vector<int> a(n);
  for(auto &x: a)cin>>x;
  Solution obj;
  cout<<obj.numberOfWays(n,x,a)<<endl;
  
}




int main() {
	ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    ll t=1;
    while(t--)
    {
        solve();
    }
	return 0;
}
