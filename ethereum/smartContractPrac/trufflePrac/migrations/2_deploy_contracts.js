const Voting = artifacts.require("./Voting.sol");

module.exports = function(deployer) {
  deployer.deploy(Voting, ['poopo', 'ita', 'saul'].map(name => web3.utils.asciiToHex(name)));
};
