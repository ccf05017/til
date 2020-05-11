import React from 'react';

const profileData = {
  poppo: {
    name: 'jbj',
    description: 'backend engineer',
  },
  ita: {
    name: 'yen',
    description: 'mother'
  }
};

function Profile({ match }) {
  const { username } = match.params;
  const profile = profileData[username];

  if (!profile){
    return <div>Not Exist user</div>
  }

  return (
    <div>
      <h3>{username} ({profile.name})</h3>
      <p>
        {profile.description}
      </p>
    </div>
  );
}

export default Profile;
