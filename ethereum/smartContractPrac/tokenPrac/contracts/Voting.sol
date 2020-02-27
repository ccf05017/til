pragma solidity >=0.4.0 <0.6.0;

contract Voting {
  struct voter {
    address voterAddress;
    uint tokensBought;
    uint[] tokenUsedPerCandidate;
  }

  mapping (address => voter) voterInfo;

  mapping (bytes32 => uint) public votesReceived;

  bytes32[] public candidateList;

  uint public totalTokens;
  uint public balanceTokens;
  uint public tokenPrice;

  constructor (uint tokens, uint pricePerToken, bytes32[] memory candidateNames) public {
    candidateList = candidateNames;
    totalTokens = tokens;
    balanceTokens = tokens;
    tokenPrice = pricePerToken;
  }

  function buy() payable public {
    uint tokensToBuy = msg.value / tokenPrice;
    require(tokensToBuy <= balanceTokens);
    voterInfo[msg.sender].voterAddress = msg.sender;
    voterInfo[msg.sender].tokensBought += tokensToBuy;
    balanceTokens -= tokensToBuy;
  }
}