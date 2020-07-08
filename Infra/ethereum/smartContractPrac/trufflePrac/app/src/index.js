import Web3 from "web3";
import votingArtifact from "../../build/contracts/Voting.json";

// 0x142Ba8A41323A77cc6CFE24a9af85bDB1cDBc213

const candidates = {
  "Rama": "candidate-1",
  "Nick": "candidate-2",
  "Jose": "candidate-3"
}

const App = {
  web3: null,
  account: null,
  meta: null,

  start: async function() {
    const { web3 } = this;

    try {
      // get contract instance
      const networkId = await web3.eth.net.getId();
      const deployedNetwork = votingArtifact.networks[networkId];
      this.voting = new web3.eth.Contract(
        votingArtifact.abi,
        deployedNetwork.address,
      );

      // get accounts
      const accounts = await web3.eth.getAccounts();
      this.account = accounts[0];

      this.loadCandidatesAndVotes();

    } catch (error) {
      console.log(error);
      console.error("Could not connect to contract or chain.");
    }
  },

  loadCandidatesAndVotes: async function() {
    const { totalVotesFor } = this.voting.methods;
    const candidateNames = Object.keys(candidates)
    
    for (let i = 0; i < candidateNames.length; i++) {
      const name = candidateNames[i];
      const count = await totalVotesFor(this.web3.utils.asciiToHex(name)).call();
      $("#" + candidates[name]).html(count);
    }
  },

  voteForCandidate: async function() {
    let candidateName = $("#candidate").val();
    $("#msg").html("Vote submitted");
    $("#candidate").val("");

    const { totalVotesFor, voteForCandidate } = this.voting.methods;
    await voteForCandidate(this.web3.utils.asciiToHex(candidateName)).send({ gas: 140000, from: this.account });
    const div_id = candidates[candidateName];
    const count = await totalVotesFor(this.web3.utils.asciiToHex(candidateName)).call();

    $("#" + div_id).html(count);
    $("#msg").html("");
  }
};

window.App = App;

window.addEventListener("load", function() {
  if (window.ethereum) {
    // use MetaMask's provider
    // App.web3 = new Web3(window.ethereum);
    // window.ethereum.enable(); // get permission to access accounts
    App.web3 = new Web3(
      new Web3.providers.HttpProvider("http://127.0.0.1:8545"),
    );
  } else {
    console.warn(
      "No web3 detected. Falling back to http://127.0.0.1:8545. You should remove this fallback when you deploy live",
    );
    // fallback - use your fallback strategy (local node / hosted node + in-dapp id mgmt / fail)
    App.web3 = new Web3(
      new Web3.providers.HttpProvider("http://127.0.0.1:8545"),
    );
  }

  App.start();
});
